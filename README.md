# Fit Cal Lab JHipster
Fit Cal Lab Project Created as a JHipster/Ionic Project

In this repo, we will build a web-page (using JHipster), an android app, and
an ios app (using the ionic framework)

## Releases
### 0.0.1
    Initial repo creation.  Edit the README.md and add .idea to .gitignore

### 0.1.0
    Add the jhipster project
        mkdir -p jhipster-app
        cd jhipster-app
        # After the jhipster command, you will be asked several questions
        # The answers that I used are shown at the end of the line
        # Select different options by using the up and down arrows
        # Hit the spacebar if multiple options are allowed
        # Just hit return to select the default (in capital letters in parenthesis)
        jhipster
        ? Which *type* of application would you like to create? Monolithic application (recommended for simple projects)
        ? [Beta] Do you want to make it reactive with Spring WebFlux? No
        ? What is the base name of your application? fitcallab
        ? What is your default Java package name? org.jhipster.fitcallab
        ? Do you want to use the JHipster Registry to configure, monitor and scale your application? No
        ? Which *type* of authentication would you like to use? JWT authentication (stateless, with a token)
        ? Which *type* of database would you like to use? SQL (H2, MySQL, MariaDB, PostgreSQL, Oracle, MSSQL)
        ? Which *production* database would you like to use? MySQL
        ? Which *development* database would you like to use? H2 with disk-based persistence
        ? Do you want to use the Spring cache abstraction? Yes, with the Ehcache implementation (local cache, for a single node)
        ? Do you want to use Hibernate 2nd level cache? Yes
        ? Would you like to use Maven or Gradle for building the backend? Gradle
        ? Which other technologies would you like to use?
        ? Which *Framework* would you like to use for the client? Angular
        ? Would you like to use a Bootswatch theme (https://bootswatch.com/)? Default JHipster
        ? Would you like to enable internationalization support? Yes
        ? Please choose the native language of the application English
        ? Please choose additional languages to install Spanish

        # There was a bug in the version I was using.  The following step should not be
        # needed; but if you have problems later, try this:
        npm install --force

        # Start up the jhipster program
        ./gradlew

### 0.2.0
    Customize app for Fit Cal Lab
    1) Create an entity relationship diagram using JHipster Design Language
       to describe the database tables/columns, and their relationships
       Use this web-page: https://start.jhipster.tech/jdl-studio/
    2) Download the JDL fitcallabjhipster.jdl file from the web-site
    3) Import the JDL file, and customize the JHipster app for Fit Cal Lab
        jhipster import-jdl fitcallabjhipster.jdl
    4) Disable faker, so it doesn't automatically generate test data
        a) edit file src/main/resources/config/application-dev.yml
        b) Change line 54, and remove faker
    5) Start the web-program
        ./gradlew