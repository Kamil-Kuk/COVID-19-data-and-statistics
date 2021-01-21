<h1>COVID-19-data-and-statistics</h1>
<h2>Table of Contents</h2>
    <ul>
        <li>General info</li>
        <li>Technologies</li>
        <li>Setup</li>
        <li>Project status</li>
    </ul>
<h2>General info</h2>
This is a repository for a project COVID-19-data and statistics written for Java course. It allow the user to display on charts
various statistics related with Covid-19 pandemic ex. the number of deaths due SARS-CoV-2 in given country in given period of time.

![Screenshot](./screenshot.png)
<br>
Data is collected from 'Our World in Data' repository (https://github.com/owid/covid-19-data/tree/master/public/data/)
<h2>Technologies</h2>
Technologies and frameworks used in this project:
    <ul>
        <li>Java</li>
        <li>Hibernate</li>
        <li>SQL</li>K
        <li>JavaFX</li>
    </ul>
<h2>Setup</h2>
In order to properly run the program follow these steps:
    <ol>
        <li>Make sure you have Java version 11+</li>
        <li>Clone this project to your local directory</li>
        <li>Create database schema named 'covid' for your root user</li>
        <li>Set password for your SQL account in .pom file</li>
        <li>Run the program with Run Configuration COVID-19-data-and-statistics' or execute [javafx:run]</li>
    </ol>
<h2>Project status</h2>
<h5>2021-01-21</h5>
Current scope of functionalities:
    <ul>
        <li>Input is given via console</li>
        <li>Program automatically update database from repository/li>
        <li>Creates new local database from .csv file</li>
        <li>Allows user to choose a country, start date, end date and one set of data</li>
        <li>Prints result of SQL query in console</li>
        <li>Prints result on chart</li>
        <li>Allows user to save results in new .csv file</li>
    </ul>