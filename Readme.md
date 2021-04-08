# Send Email List of Users

![emailInde](https://user-images.githubusercontent.com/37740006/114088618-05585e00-98d7-11eb-81a0-1d40cc369d0f.png)

![addEM](https://user-images.githubusercontent.com/37740006/114088701-1a34f180-98d7-11eb-8f75-80eefeb2d9ee.png)

![listUse](https://user-images.githubusercontent.com/37740006/114088752-291ba400-98d7-11eb-9cf6-d5060e058a4e.png)

# Step 1: Create Spring Boot project

Create Spring boot priject from SpringInitializer.

group-name : springboot-email-send-list-of-user
artifact-name : com.emailSendApp

### Dependencies
		
		1. SpringBoot devtools
		2. SpringBoot data jap
		3. SpringBoot web
		4. SpringBoot thymeleaf
		5. SpringBoot Mysql driver



# Step 2: Add the Spring Boot Starter Mail dependency in your pom.xml file.
```.xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```
# Step 3: Create some directory inside the project

	1. controller
	2. repository
	3. service
	4. model
	5. serviceimpl


# Step 4 : Create Employee Model

```
package com.listsentmail.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer emp_id;
	private String emp_name;
	private String emp_address;
	private String emp_email;

	public Integer getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getEmp_address() {
		return emp_address;
	}

	public void setEmp_address(String emp_address) {
		this.emp_address = emp_address;
	}

	public String getEmp_email() {
		return emp_email;
	}

	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}

}


```

# Step 5 : Repository 

```java
package com.listsentmail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.listsentmail.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}

```
# Step 6 : EmployeeService
```java

package com.sendEmail.emailsendinglistofuser.service;

import java.util.List;

import com.sendEmail.emailsendinglistofuser.model.Employee;

public interface EmployeeService {
	
	List<Employee> getAllEmployeeList();
	
	void saveEmployee(Employee employee);
		
}

```

# Step 7 : EmployeeServiceImpl
```java
package com.sendEmail.emailsendinglistofuser.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendEmail.emailsendinglistofuser.model.Employee;
import com.sendEmail.emailsendinglistofuser.repository.EmployeeRepository;
import com.sendEmail.emailsendinglistofuser.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployeeList() {
		return employeeRepository.findAll();
		
	}

	@Override
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
		
	}

}


```

# Setp 4 : Create MainController for display welcome page

```

@Controller
public class MainController {

	@GetMapping("/")
	public String welcome() {

		return "welcome";
	}
```


# Step 5 : Create Welcome page
```.html

<html>

<head>
	<title>welcome</title>
</head>


<body>

<h1>Welcome Email Sending App</h1>

<a th:href="@{/addEmployee}">Add Employee</a>

<a th:href="@{/employeeList}">List of Employee</a>

</body>

</html>

````

# Setp 6 : Add Employee Page:
```html
<html>

<head>
<title>Add employee</title>
</head>


<body>

	<form th:action="@{/addEmployee}" method="post" th:object="${employee}">

		<label>Employee Name</label><br /> <input type="text" th:field="*{emp_name}"><br /> 
		<label>Address</label><br />
		<input type="text" th:field="*{emp_address}"><br /> <label>Employee Email</label><br>
		<input type="text" th:field="*{emp_email}"><br> <input type="submit"><br>

	</form>


</body>

</html>
```

# Step 7 : list of Employee 
```html
<html>

<head>
<title>Add employee</title>
</head>


<body>

	<table border="2" align="center">
		<thead>
			<tr>
				<th>Empolyee Name</th>
				<th>Employee Address</th>
				<th>Employee Email</th>
				<th>Send Email</th>
			</tr>
		</thead>
		<tbody>
			<tr th:each="e : ${employees}">
				<td th:text="${e.emp_name}"></td>
				<td th:text="${e.emp_address}"></td>
				<td th:text="${e.emp_email}"></td>
				<td><a href="/sendMail/${e.emp_id}">Mail</a></td>
			</tr>
		</tbody>

	</table>

</body>

</html>
```
# Step : Create configuration file

create config package and inside config package create MailConfig class

```java
package com.sendEmail.emailsendinglistofuser.config;


import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Value("smtp.gmail.com")
	private String host;

	@Value("587")
	private Integer port;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(host);
		javaMailSender.setPort(port);
		javaMailSender.setUsername("your email");
		javaMailSender.setPassword("your Password");
		javaMailSender.setJavaMailProperties(getMailProperties());

		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "false");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.debug", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");

		return properties;
	}
}

```

# Step : Update EmployeeController @Autowired and add method for sending email
```java
	@GetMapping("/sendMail/{employee_id}")
	public ResponseEntity<Employee> employeeMail(@PathVariable("employee_id") Integer employee_id) {
		Employee employee = employeeService.findEmployeeById(employee_id);

		sendEmail(employee);

		return new ResponseEntity<Employee>(HttpStatus.OK);
	}

	private void sendEmail(Employee employee) {
		final String emailToRecipient = employee.getEmp_email();
		final String emailSubject = "Succesfully Registration";

		final String emailMessage1 = "<html> <body> <p>Dear Sir/Madam,</p><p>You have succesfully Registered with our Services"
				+ "<br><br>"
				+ "<table border='1' width='300px' style='text-align:center;font-size:20px;'><tr> <td colspan='2'>"
				+ "</td></tr><tr><td>Name</td><td>" + employee.getEmp_name() + "</td></tr><tr><td>Address</td><td>"
				+ employee.getEmp_address() + "</td></tr><tr><td>Email</td><td>" + employee.getEmp_email()
				+ "</td></tr></table> </body></html>";
		mailSenderObj.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMessageHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelperObj.setTo(emailToRecipient);
				mimeMessageHelperObj.setText(emailMessage1, true);
				mimeMessageHelperObj.setSubject(emailSubject);

			}
		});
	}

```

# EmployeeController
```java
package com.sendEmail.emailsendinglistofuser.controller;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sendEmail.emailsendinglistofuser.model.Employee;
import com.sendEmail.emailsendinglistofuser.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	
	@Autowired private JavaMailSender mailSenderObj;
	
	
	@GetMapping("/")
	public String welcomePage() {
		return "welcome";
	}

	@GetMapping("/addEmployee")
	public String addEmployeeForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "addEmployee";
	}

	@PostMapping("/addEmployee")
	public String addEmployee(@ModelAttribute Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/employeeList";
	}

	@GetMapping("/employeeList")
	public String employeeLists(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployeeList());
		return "employeeList";
	}

	@GetMapping("/sendMail/{employee_id}")
	public ResponseEntity<Employee> employeeMail(@PathVariable("employee_id") Integer employee_id) {
		Employee employee = employeeService.findEmployeeById(employee_id);

		sendEmail(employee);

		return new ResponseEntity<Employee>(HttpStatus.OK);
	}

	private void sendEmail(Employee employee) {
		final String emailToRecipient = employee.getEmp_email();
		final String emailSubject = "Succesfully Registration";

		final String emailMessage1 = "<html> <body> <p>Dear Sir/Madam,</p><p>You have succesfully Registered with our Services"
				+ "<br><br>"
				+ "<table border='1' width='300px' style='text-align:center;font-size:20px;'><tr> <td colspan='2'>"
				+ "</td></tr><tr><td>Name</td><td>" + employee.getEmp_name() + "</td></tr><tr><td>Address</td><td>"
				+ employee.getEmp_address() + "</td></tr><tr><td>Email</td><td>" + employee.getEmp_email()
				+ "</td></tr></table> </body></html>";
		mailSenderObj.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMessageHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelperObj.setTo(emailToRecipient);
				mimeMessageHelperObj.setText(emailMessage1, true);
				mimeMessageHelperObj.setSubject(emailSubject);

			}
		});
	}

}
```
