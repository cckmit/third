@rem mvn war:war -Dmaven.test.skip=true

mvn install:install-file -DgeneratePom=true -DartifactId=opoo-apps -DgroupId=org.opoo.war -Dfile=target\opoo-apps-1.6.8.war -Dversion=1.6.8 -Dpackaging=war

