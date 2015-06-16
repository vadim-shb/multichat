var gulp = require('gulp');
var del = require('del');
var inject = require('gulp-inject');
var browserSync = require('browser-sync');
var concat = require('gulp-concat');

//=========================================== configs ===========================================

var config = {
    jsSource: [
        'src/modules/**/*.js',
        'src/app.js',
        'src/utils/**/*.js',
        'src/widgets/**/*.js',
        'src/pages/**/*.js'
    ],
    cssSource: [
        'src/css/**/*.css'
    ],
    bowerJsForTest: [
        'bower_components/angular-mocks/angular-mocks.js'
    ],
    jsTestSource: [
        'test/**/*.js'
    ]
};

var devConfig = {
    libFolder: 'src/lib',

    bowerJs: [
        'bower_components/jquery/dist/jquery.js',
        'bower_components/angular/angular.js',
        'bower_components/angular-route/angular-route.js',
        'bower_components/bootstrap/dist/js/bootstrap.js'
    ],
    bowerCss: [
        'bower_components/bootstrap/dist/css/bootstrap.css',
        'bower_components/bootstrap/dist/css/bootstrap.css.map'
    ],
    libJs: [
        'src/lib/jquery.js',
        'src/lib/angular.js',
        'src/lib/angular-route.js',
        'src/lib/**/*.js'
    ],
    libCss: [
        'src/lib/**/*.css'
    ]
};

//=========================================== helpers ===========================================

function copy(srcMask, destFolder) {
    return gulp.src(srcMask).pipe(gulp.dest(destFolder))
}

function bower2lib(bowerSources, libFolder) {
    del.sync(libFolder + '/*');
    return copy(bowerSources, libFolder);
}

function fillIndex(sources, destination) {
    var gulpedSources = gulp.src(sources, {read: false});

    return gulp.src('src/index.html')
        .pipe(gulp.dest(destination))
        .pipe(inject(gulpedSources, {relative: true}))
        .pipe(gulp.dest(destination));
}

//==================================== developer environment ====================================
gulp.task('dev.libs2sources', function() {
    var bowerSources = devConfig.bowerJs.concat(devConfig.bowerCss);
    return bower2lib(bowerSources, devConfig.libFolder);
});

gulp.task('dev.sources2index', function() {
    var sources = devConfig.libJs
        .concat(config.jsSource)
        .concat(devConfig.libCss)
        .concat(config.cssSource);

    return fillIndex(sources, 'src')
});

gulp.task('dev.browserSync.reload', gulp.series('dev.sources2index', browserSync.reload));

gulp.task('dev.watch', function() {
    gulp.watch('src/**/*', gulp.series('dev.browserSync.reload'));
    gulp.watch('bower_components/**/*', gulp.series('dev.libs2sources', 'dev.browserSync.reload'));
});

gulp.task('dev.browserSync.start', function() {
    browserSync({
        server: {
            baseDir: './src/'
        },
        port: 9090,
        https: false,
        ui: {
            port: 9091
        },
        notify: false,
        //browser: 'google-chrome-stable'
        browser: 'firefox'
    });
});

gulp.task('dev', gulp.series('dev.libs2sources', 'dev.sources2index', gulp.parallel('dev.browserSync.start', 'dev.watch')));

