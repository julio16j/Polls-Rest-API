# Polls-Rest-API
### Poll-Rest-API is an API aimed at creating binary polls having "yes" or "no" answers, it is currently published on [Azure](https://poll-rest-api.azurewebsites.net/).
![swaggerPollScreenshot](https://user-images.githubusercontent.com/39336736/212930353-11a1703e-909a-4a15-960c-61a4cc870c8e.png)
### To publish in cloud was used github actions with docker.
![image](https://user-images.githubusercontent.com/39336736/212931932-b3824def-238d-48fe-9747-0844e42e98bc.png)
### So  after any commit on the branch main de code will be deployed to azure cloud,
### And besides, this project has 2 enviroments with 2 diferents databases, the production env with a postegresql db and the develop env with a h2 db.
### Before the deploy step the code is tested during the "mvn clean install" command, now has 60% coverage rate, only testing the service layer.
![image](https://user-images.githubusercontent.com/39336736/212935096-12534bd8-7f7c-422a-8e7a-d65a0c9651d2.png)
