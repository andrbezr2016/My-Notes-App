call mvn clean package
call docker build . --rm -t bezrukov/my-notes-app:1.0
#call docker push my-notes-app:1.0