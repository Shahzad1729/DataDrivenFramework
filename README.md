# DataDrivenFramework

# 🧪 Data Driven Automation Framework - Selenium + TestNG + Maven

This is a **Data Driven Automation Framework** built using **Selenium WebDriver**, **TestNG**, **Maven**, and integrated with **Log4j**, **Excel (Apache POI)**, **Extent Reports**, and **ReportNG** for reporting. The project uses Excel files as external data sources to drive test cases.

---

## 📁 Project Structure

src
├── main
│ └── java
│ └── dd.example.pages # (For Page classes if needed in future)
├── test
│ ├── java
│ │ └── dd.example
│ │ ├── base # Base class (WebDriver setup, utilities)
│ │ ├── listeners # Custom Listeners (screenshot on failure, Extent integration)
│ │ ├── rough # Temporary test files (for trials)
│ │ ├── testcases # All actual test classes
│ │ └── utilities # Reusable helper methods (Excel Reader, Screenshot Utils)
│ └── resources
│ ├── excel # Excel test data files
│ ├── executables # Drivers if needed
│ ├── logs # Selenium.log, Application.log
│ ├── properties # config.properties, locators.properties
│ └── runner
│ └── testRunner.xml # TestNG XML suite runner

---

## 🔧 Tools & Technologies

| Tool             | Purpose                                      |
|------------------|----------------------------------------------|
| **Java**         | Core language for test implementation        |
| **Selenium**     | Web browser automation                       |
| **TestNG**       | Test management and assertions               |
| **Maven**        | Build and dependency management              |
| **Log4j**        | Logging test steps and events                |
| **ReportNG**     | HTML/XML Test Reports                        |
| **ExtentReports**| Rich test reporting with screenshots         |
| **Apache POI**   | Reading data from Excel                      |

---

## ⚙️ How to Set Up

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/data-driven-framework.git
   cd data-driven-framework
Import into IntelliJ or Eclipse

Open as Maven project

Let it resolve dependencies

Install browser drivers (if not using WebDriverManager)

Place drivers in /executables folder

Update config.properties with:

browser=chrome
siteUrl=https://example.com
implicitWait=7
Run the tests

From terminal:

mvn clean test
Or directly from IntelliJ using testRunner.xml

📋 Features
✅ Fully modular framework using Page Object Model (if extended)

✅ External Excel data support using Apache POI

✅ Centralized configuration using .properties files

✅ Screenshots on failure

✅ Parallel TestNG compatibility

✅ Dual Reporting: ReportNG + Extent

✅ Logging via Log4j

📸 Reports & Logs
Extent Report: target/surefire-reports/html/extent.html

ReportNG HTML: target/surefire-reports/html/index.html

Application Logs: resources/logs/Application.log

Selenium Logs: resources/logs/Selenium.log

Failure Screenshots: Embedded in Extent/ReportNG reports

🧪 Sample Excel Format
firstname	lastname	postcode	alertText
John	Doe	123456	Customer added

🤝 Contributing
Fork the repository

Create your branch (git checkout -b feature-xyz)

Commit changes (git commit -am 'Add new feature')

Push to branch (git push origin feature-xyz)

Open a Pull Request