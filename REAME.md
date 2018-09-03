#Tender
This application was created with SPRING INITIALIZR. You can find documentation and help at [https://start.spring.io/](https://start.spring.io/)

## Development


## Database settings
For this example application, I'm using the in-memory [H2 Database Engine](http://www.h2database.com/html/main.html), since no server installation is needed.
After the application is started, H2 console can be accessed on /h2-console with user 'sa' and password 'sa'.

### Database Migration
For the database migration, [Liquidbase Project](https://www.liquibase.org/) has been chosen cause it's database-independent. It can easily be changed latter to support any other SQL database server, without changing the line of code.