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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sendEmail.emailsendinglistofuser.model.Employee;
import com.sendEmail.emailsendinglistofuser.repository.EmployeeRepository;
import com.sendEmail.emailsendinglistofuser.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private JavaMailSender mailSenderObj;

	@Autowired
	private EmployeeRepository employeeRepository;

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

	// sendmail code
	@GetMapping(value = "/sendMail/{employee_id}")
	public ResponseEntity<Employee> employeeMail(@PathVariable("employee_id") Integer employee_id) {

		Employee employee = employeeRepository.findById(employee_id).get();

		sendmail(employee);

		return new ResponseEntity<Employee>(HttpStatus.OK);

	}

	private void sendmail(Employee employee) {

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

				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setText(emailMessage1, true);

				mimeMsgHelperObj.setSubject(emailSubject);

			}
		});

	}

}
