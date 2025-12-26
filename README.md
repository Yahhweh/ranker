# ranker

A console-based application designed to process Formula 1 race logs, calculate lap durations, and generate ranked performance reports.

---

## Project Overview

### Functional Features
* **Log Parsing**: Extraction of data from three distinct source files (abbreviations, start timestamps, and end timestamps).
* **Time Calculation**: Precise lap duration computation using Java Time API.
* **Ranking Logic**: Sorting drivers by lap time from fastest to slowest.
* **Formatted Output**: CLI report generation with a visual separator highlighting the top 15 performers.

### Technical Stack
* **Language**: Java 11+ (utilizing Stream API and Time API).
* **Build System**: Maven.
* **Testing**: JUnit 5, AssertJ.

---

## Architecture Specification

The application follows a modular design focused on the separation of data parsing, business logic, and view formatting.

### 0) Application Orchestration
* **`Main`**: The system entry point that initializes the execution flow.
* **`RaceController`**: Acts as a central orchestrator, coordinating file reading, data processing, and final output delivery.

### 1) Data Models (`models` package)
* **`Driver`**: Represents a pilot entity containing their abbreviation, full name, and team name.
* **`LapResult`**: A composite model linking a `Driver` object with their calculated `Duration`.

### 2) Parsing Layer (`parsers` package)
* **`DataParser`**: A base component for resource handling and line filtering.
* **`DriverParser`**: Specifically handles the mapping of abbreviations to pilot identities.
* **`TimeParser`**: Responsible for extracting start and end timestamps and associating them with specific driver codes.

### 3) Logic Layer (`services` package)
* **`DurationFinder`**: Executes the mathematical logic to find the difference between start and finish timestamps.
* **`LapResultCreator`**: Merges parsed driver data with time metrics to build the final result set.
* **`Ranking`**: Sorts the result collection based on lap duration in ascending order.

### 4) Formatting & Output (`formatter`, `printers` packages)
* **`ReportFormatter`**: Transforms raw data into a structured string table, inserting a logical break after the 15th position.
* **`ReportPrinter`**: Manages the physical delivery of the formatted report to the console.

---

## Resource Requirements

The application processes three mandatory input files:
1.  **`abbreviations.txt`**: Driver mapping (e.g., `SVF_Sebastian Vettel_FERRARI`).
2.  **`start.log`**: Start time records (e.g., `SVF2018-05-24_12:02:58.917`).
3.  **`end.log`**: Finish time records (e.g., `SVF2018-05-24_12:04:03.332`).

---

## Quality Assurance

* **Unit Testing**: Complete coverage of parsers, time calculation logic, and report formatting using JUnit 5.
* **Validation**: Extensive use of AssertJ for fluent and readable assertions.
* **Error Handling**: Verification of system behavior against malformed logs and missing data points.

---

## Installation & Execution

### Build
```bash
mvn clean install
