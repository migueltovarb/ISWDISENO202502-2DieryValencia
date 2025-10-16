package ProjectEmpleado;

public class Empleado {
	
				
		private int id;
						
		public int getId() {
				return this.id; 
		}
		
		
		private String firstName;
		
		public String getFirstName() {
			return this.firstName;
		}
		
		private String lastName;
		
		public String getLastName() {
			return this.lastName;
		}
		
		private int salary;
		
		public int getSalary() {
			return this.salary;
		}
		
		
		public void setSalary(int salary) {
			this.salary = salary;
		}
		
		public String getName() {
			 return firstName + " " + lastName;
		}
		
		public void getAnnualSalary(int Salary) {
			this.salary = salary * 12;
		}
		
		public int raiseSalary(int percent) {
			salary += (salary * percent) / 100;
			return salary;
		}
		
		@Override
		public String toString() {
			return "Empleado[id= "+ id +",name= " + firstName + " "+  lastName + ",salary= "+salary + "]";
		}

}
