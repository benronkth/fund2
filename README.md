# fund2
Assignment 2


Goal
The goal of this assignment is to master the core of continuous integration. To achieve this goal, the students are asked to implement a small continuous integration CI server. This CI server will only contain the core features of continuous integration. The features are all specified below, as grading criteria.

The grading focuses on the understanding and implementation of the core CI features, but also considers the application of software engineering on the development process, see the grading scheme below.

Development
You must use a development platform (Github, Bitbucket); KTH github itself has no webhook support so it cannot be used to trigger your CI service. Only the content published on the development platform is used for grading. The content means: 1) the code 2) the issues 3) the pull requests 4) the continuous integration data (if available).

The students can start the task right now, and have to be done at the final grading session for this assignment. Final changes can be made after the grading, up to the deadline.

Architecture
The figure shows the overall architecture of the problem. Note that you are not required to use GitHub (for the repository) and ngrok (to make your server externally visible); you can also use e-mail notification instead of a web front-end. This is just an example.

CI workflow when using GitHub notifications

Roles
Roles of the students: coordinate in the group, ensure everybody is able to contribute, do the work, deliver on time, ask for feedback on the Canvas discussion forum, present the work during the grading appointment.

Roles of the teaching assistants: provide support for students during laboratory sessions, provide support on the Canvas discussion forum, prepare a first version of the grading form.

Roles of the lecturers: final assessment and official grading.

Ethics
Recall that the student code of conduct forbids any kind of plagiarism, between groups in the course, or based on content taken on the internet.

Notes
[prerequisite] We assume basic knowledge of HTTP networking, and its handling in Java. This is required to understand the webhook mechanism of Github (Links to an external site.). A basic skeleton implementation a webhook-based CI server is available at https://github.com/KTH-DD2480/smallest-java-ci.  (Links to an external site.)The skeleton is optional, it is provided to help the students getting started. The students can use other languages or frameworks.
[prerequisite] Basic knowledge of REST (Links to an external site.) is strongly recommended. It is required to be able to notify users about CI results with commit statuses (see P3 below), and also for P6.
[recommendation] Dog fooding: it is recommended that the group uses its own CI server during development.
[recommendation] Multiple CI: it is possible to activate several CI services on the same repository. For instance, the students can activate a normal CI service on their repository (e.g., GitHub actions), in addition to their your own CI server.
[challenge] Setting up a server is easy if one has access to a machine that is connected on the Internet, with no firewall or network rules blocking incoming requests (there are several KTH machines such as student-shell.sys.kth.se, student-shell-2.sys.kth.se, student-shell-3.sys.kth.se, with which you have to use ngrok, though). If the students do not have access to such a machine, there is always the solution to tunnel incoming requests: ngrok is a really good solution for this, and its usage is documented in https://github.com/KTH-DD2480/smallest-java-ci (Links to an external site.)
[convention] If we all use the same KTH machine, we have to be careful of not using the same network port. The convention is that the students use the port 8000+<group number> (eg port 8012 for group 12, "python -m SimpleHTTPServer 8012")
