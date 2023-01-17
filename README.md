# Polls-Rest-API
Poll-Rest-API is an API aimed at creating binary polls having "yes" or "no" answers, it is currently published on Azure available oh this link [https://poll-rest-api.azurewebsites.net/](https://poll-rest-api.azurewebsites.net/).

### Technologies
This project used the following: Java (JDK17), Spring Boot, Docker, PostegreSql.

![swaggerPollScreenshot](https://user-images.githubusercontent.com/39336736/212930353-11a1703e-909a-4a15-960c-61a4cc870c8e.png)
### Deploy with CI/CD - Continuos Integration and Delivery
![image](https://user-images.githubusercontent.com/39336736/212931932-b3824def-238d-48fe-9747-0844e42e98bc.png)

To publish in cloud was used github actions with docker,
so after any commit on the branch main, the code will compile and builded the docker image, to be deployed to azure cloud.

### Testing
This project has 2 enviroments with 2 diferents databases, the production env with a postegresql db and the develop env with a h2 db.

Before the deploy step the code is tested during the "mvn clean install" command, now has 60% coverage rate, only testing the service layer.

![image](https://user-images.githubusercontent.com/39336736/212935096-12534bd8-7f7c-422a-8e7a-d65a0c9651d2.png)
