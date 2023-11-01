# EduTrack

Here is a glimpse of what EduTrack looks like!

[![CI Status](https://github.com/se-edu/addressbook-level3/workflows/Java%20CI/badge.svg)](https://github.com/AY2324S1-CS2103T-T15-3/tp/actions)

![Ui](docs/images/Ui.png)

### **EduTrack** provides a **centralised system** to help teaching assistants manage multiple classes.<br>
You should expect **EduTrack** to be able to manage:
* Student attendance
* Additional notes for each student

### Installation:
* Ensure you have java 11 installed in your local computer (Download [here](https://www.oracle.com/sg/java/technologies/javase/jdk11-archive-downloads.html) if you have yet to do so!)
* Download the [JAR file](https://github.com/AY2324S1-CS2103T-T15-3/tp/releases)
* Run it!

### Quick Start
Follow the following commands to get started in setting up your EduTrack!

**Create your class**
```
add /c cs2103t
```
`cs2103t` can be replaced by the name of your class

**Add your student to the class**
```
add /s John /c 1
```
`1` - can be replaced by your class index which you can see using `list` command

**Add your personalised class note**
```
edit /c 1 /m Reminder to take attendance!
```
`1` - can be replaced by your class index which you can see using `list` command

**Mark your class attendance**
```
startlesson /c cs2103t
mark /s 1 /c cs2103t
```
`cs2103t` - can be replaced by the name of your class

`1` - can be replaced by the student index of your class which you can view using `view /c` command

**Refer to our [user guide](https://ay2324s1-cs2103t-t15-3.github.io/tp/UserGuide.html) to see what else you can do!**

### Contributors:
  * Sean
  * Sarah
  * Kota
  * Helin
  * Donovan

  Learn more [AboutUs](https://github.com/AY2324S1-CS2103T-T15-3/tp/blob/master/docs/AboutUs.md)!

This project is based on the AdrdessBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
