# Java DropWizard Flyway Starter

Database Migration - Local
---

1. Add your SQL script to `resources.db.migration` directory
2. Add the following lines to your ~/.zshrc file:

```
export FLYWAY_URL="jdbc:mysql://YOUR_DB_HOST/YOUR_DB_NAME"
export FLYWAY_USER="YOUR_DB_USERNAME"
export FLYWAY_PASSWORD="YOUR_DB_PASSWORD"
export FLYWAY_BASELINE_ON_MIGRATE=true
```

3. Reload your terminal session if required:

```
. ~/.zshrc
```

4. Run Flyway command through Maven:

```
mvn flyway:migrate
```

Database Migration - Production
---

1. Add following secrets to your Github repo:

```
DB_USERNAME - the prod db username
DB_PASSWORD - the prod db password
DB_HOST - the prod db host
DB_NAME - the prod db name
```

2. Raise a pull request with your script in the `resources.db.migration` directory
3. After approvals, merge pull request; this will trigger the migration action to run in Github
4. Ensure migration successfully runs against prod database

How to start the test application
---

1. Set the following environment variables:
    1. DB_USERNAME
    2. DB_PASSWORD
    3. DB_HOST
    4. DB_NAME
2. Run `mvn clean install` to build your application
3. Run `nvm test` to run unit and integration tests.
1. You can start application via:
    1. IDE: Control + R
    2. IDE: Edit run configuration -> Add `server` to program arguments -> Run
1. To check that your application is running enter url `http://localhost:8080/swagger`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

To see how to run the Front end <a href="https://github.com/jackpollinsK/team_4_Frontend" target="_blank">here</a>
