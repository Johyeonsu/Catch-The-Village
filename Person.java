package TeamProject;

public class Person extends Village {

	boolean personTF;
	String jobName;
	int plum;
	double happy;
	
	static Person[] person = new Person[100];

	public static Person[] getPerson(){
		return Person.person;
	}
	public static void setPerson(Person person[]) {
		Person.person = person;
	}
	
	
	public Person() {
		this.personTF = false;
		this.jobName = "";
		this.plum = 0;
		this.happy = 0.0;
	}

	public Person(boolean personTF, String jobName, int plum, double happy) {
		this.personTF = personTF;
		this.jobName = jobName;
		this.plum = plum;
		this.happy = happy;

	}

	Person changeJob(Person person, int index) {
		switch (index) {
		case (0): // 목수
			person = new Person(true, "목수", 10, 1.0);
			break;
		case (1): // 어부
			person = new Person(true, "어부", 8, 1.0);
			break;
		case (2): // 농부
			person = new Person(true, "농부", 14, 1.0);
			break;
		case (3):// 상인
			person = new Person(true, "상인", 20, 1.0);
			break;
		case (4): // 광부
			person = new Person(true, "광부", 18, 1.0);
			break;
		case (5):// 백수
			person = new Person(true, "Jobless", 0, 0.0);
			break;
		case (6):// 노숙자
			person = new Person(true, "Homeless", 0, -1.0);
			break;
		}
		return person;
	}
}
