call mvn -B -s settings.xml -DskipTests=true clean package
call java -Dspring.profiles.active="jpa,heroku" -DDATABASE_URL="postgres://user:password@localhost:5432/gamblingstation" -jar target/dependency/webapp-runner.jar target/*.war