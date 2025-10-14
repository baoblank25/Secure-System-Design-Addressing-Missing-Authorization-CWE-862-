Secure System Design — Addressing CWE-862: Missing Authorization

Author: Brian Bao Hoang
Arizona State University — Barrett Honors Contract Project

📘 Overview

This project demonstrates the detection and mitigation of CWE-862: Missing Authorization, one of the MITRE Top 25 Software Weaknesses (2024)
.
Missing Authorization occurs when an application fails to verify whether a user is allowed to access a specific feature or resource — even if they are authenticated.

The project implements multiple security mechanisms to enhance authorization and reduce misuse, including:

🔒 Account lockouts after multiple failed login attempts

⏱️ Session timeouts for inactive users

🧾 Audit logging

These features strengthen access control and fulfill the Barrett Honors Contract requirement by emphasizing ethical and secure system design.

⚠️ Problem Background
CWE-862: Missing Authorization

Occurs when a system authenticates a user but does not verify their permissions before granting access to protected actions or data.

Risks addressed:

Brute-force attacks on weak or repeated login attempts

Absence of activity logging → undetected malicious behavior

Security impact: Data exposure, privilege escalation, unauthorized data modification.

🧩 System Features
Component	Description
Login Security	Limits repeated login attempts; locks account after exceeding threshold.
Session Tracker	Detects inactivity; expires sessions after 5–10 minutes idle time.
Audit Logger	Records all user activity (login, logout, privilege changes, deletions).
Role-Based Access Control (RBAC)	Prevents unauthorized role escalation and sensitive data access.
🧠 System Design Summary

Without Fixes:

Sessions never expire

Login brute-force attacks possible

No accountability for actions

With Fixes:

Lockout after multiple failed logins

Automatic session timeout after inactivity

Full audit trail for sensitive operations

🧪 Testing & Validation
Test	Expected Result
Lockout Test	Multiple failed logins → temporary account lock.
Session Timeout Test	Idle for >5 minutes → re-authentication required.
Audit Test	All login, logout, and privilege changes recorded in log.
Access Control Test	Unauthorized actions trigger alerts or access denial.
🚀 How to Run
1. Clone the Repository
git clone https://github.com/baoblank25/Secure-System-Design-Addressing-Missing-Authorization-CWE-862-.git
cd Secure-System-Design-Addressing-Missing-Authorization-CWE-862-

2. Build and Compile

Use any Java IDE (e.g., IntelliJ, Eclipse) or the command line:

javac -d bin -cp . src/**/*.java

3. Run the Application
java -cp bin application.MainApp

4. Database Setup

Ensure the Database.java file points to your configured database.
If using a local SQLite or MySQL database, verify:

Correct credentials

Proper user table with roles and login tracking

💡 Implementation Details

This project uses:

JavaFX UI: Manages login and session interface

Database.java: Handles account storage, failed attempts, and audit logs

AuditLogger: Saves logs for accountability and traceability

SessionManager: Tracks idle time and enforces timeout-based logout

🧱 Requirements Implemented
ID	Requirement	Implemented Behavior
R1	Account lockout after repeated failed logins	Account temporarily disabled
R2	Session timeout after 5–10 minutes idle	Auto logout + re-authentication
R3	Activity audit logging	All key actions recorded
R4	Prevent role escalation	Checks ensure least-privilege operation
📜 References

MITRE CWE-862: Missing Authorization

OWASP Access Control Cheat Sheet

NIST SP 800-63B — Authentication & Lifecycle Management

Google, Microsoft, and GitHub security guidelines

🧭 Conclusion

This project reinforces the principle that authorization must be explicit, enforced, and verifiable.
By integrating login lockouts, session timeouts, and audit logging, the system achieves greater resilience against unauthorized access and aligns with ethical software engineering standards.

“Security should not be an afterthought — it must be designed into the system from the start.”
