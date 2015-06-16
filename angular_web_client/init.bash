#!/bin/bash
cd "$(dirname "$0")"
#sudo aptitude install nodejs
#sudo npm install -g bower
###sudo npm install -g gulp ## old version for now
#sudo npm install -g gulpjs/gulp-cli#4.0
#sudo npm install -g jasmine
#sudo npm install -g karma-cli
#echo fs.inotify.max_user_watches=524288 | sudo tee -a /etc/sysctl.conf && sudo sysctl -p

bower install
npm install
gulp dev