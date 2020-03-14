package TeamProject;

import TeamProject.Product;

public class Village {
	Village village;
	Product[] product;
	static String villageName = ""; // 마을명
	
	static int villagePopulation=5; // 전체인구
	static int jobMember=0;			// 직업인 수
	static int joblessMember=0;		// 백수 수
	static int homelessMember=0;	// 노숙자 수
	static int villageResidents;	// 거주지 수
	
	static double villageHappy=1.0; // 마을행복도
	static int villagePlum=400; // 마을플럼
	static String playType="continue"; // 플레이타입
	
	
	
	public Village() {
		this.village = this;
	}

	public void populationCount(Person[] people, Product[] product) {							// 시간 당 인구채크 메소드
		int i, j, count=0, jobmembercount=0, joblesscount=0, homelesscount=0;
		for (i = 0; i < people.length; i++) {
			if(people[i].personTF == true && people[i].happy == 1.0) {
				jobmembercount += 1; count+=1;
			}else if(people[i].personTF == true && people[i].jobName == "Jobless"){
				joblesscount += 1; count+=1;
			}else if(people[i].personTF == true && people[i].jobName == "Homeless"){
				homelesscount += 1; count+=1;
			}
		}
		Village.villagePopulation = count;	//전체인구수
		Village.jobMember = jobmembercount;	//직업인수
		Village.joblessMember = joblesscount;	//백수수
		Village.homelessMember = homelesscount;	//노숙자수
		Village.villageResidents = Product.sumResidentsIn(product);	//거주지수
		
		if ((Village.villagePopulation) > 50) {												 // 엔딩
			Village.playType = "happyending";
		}else if ((Village.villagePopulation) < 5) { 
			for (i = 0; i < people.length; i++) {
				people[i] = new Person();
			}
			Village.playType = "sadending";
		}
		int k=0;
		// 거주지가 있어서 노숙자 옮기기
		if (Village.villageResidents > (Village.jobMember + Village.joblessMember) && Village.homelessMember > 0) {
			for (i = 0; i < Village.villageResidents - (Village.jobMember + Village.joblessMember); i++){
				for (j = 0; j < people.length; j++) {
					if (people[j].jobName == "Homeless") {
						people[j] = people[j].changeJob(people[j], 5);
						for (k = 0; k < product.length; k++) {
							if (product[k].name == "homeless") {
								product[k] = new Product();
								break;
							}
						}
					}
				}
			}
		}
		System.out.println(" 전체인구"+Village.villagePopulation+" 직업인"+ Village.jobMember+" 백수"+Village.joblessMember +" 노숙자"+ Village.homelessMember+" 거주가능"+Village.villageResidents);
	
	};


	public void happyAve(Person[] people, Product[] product) { 								// 행복도 평균 계산 메소드
		double happysum = 0;
		double happyave = 1;
		int count = 0;

		// 각 조형물 별 행복도 계산
		for (int i = 0; i < Village.villagePopulation; i++) {
			happysum += people[i].happy;
			count++;
		}
		happyave = happysum / (double) count;
		happyave = Math.round(happyave * 100) * Product.sumHappy(product);
		Village.villageHappy = happyave;

	}

	public void plumIncrease(Person[] people, Product[] product) { 							// 분당 세금 징수
		int plum = 0;
		for (int i = 0; i < Village.villagePopulation; i++) {
			plum += people[i].plum;
		}
		Village.villagePlum += plum;
		
	}

	
	
	
	
	
	public void populationChange(Person[] people, Product[] product) { 						// 시간 당 인구 증감 메소드
		int i = 0, count = 0;
		int population = 0;

		if (Village.villageHappy >= 100.0)
			population = 3;	
		else if (Village.villageHappy < 100.0 && Village.villageHappy >= 80.0)
			population = 2;
		else if (Village.villageHappy < 80.0 && Village.villageHappy >= 50.0)
			population = 1;
		else if (Village.villageHappy < 50.0 && Village.villageHappy >= 30.0)
			population = -5;
		else if (Village.villageHappy < 30.0)
			population = -10;
		
		System.out.println("변경인구 : " + (Village.villagePopulation + population) + "(" + population + ")\n");
		
		
		int j=0;

		if (population > 0) { // 인구가 증가하는 경우
			System.out.println("인구가 증가해요");
			for (i = Village.villagePopulation; i < (Village.villagePopulation + population); i++) {
				if (Village.villageResidents > Village.jobMember + Village.joblessMember) {
					for (j = 0; j < people.length; j++) {
						if (people[j].personTF == false) {
							people[j] = people[j].changeJob(people[j], 5); // 백수
							break;
						} else {
							continue;
						}
					}
					Village.joblessMember++;
				} else {
					for (j = 0; j < people.length; j++) {
						if (people[j].personTF == false) {
							people[j] = people[j].changeJob(people[j], 6); // 노숙자
							product[99 - j] = product[99 - j].makeHomeless(product[99 - j]);
							break;
						} else {
							continue;
						}
					}
				}
			}
		} else
		
		if (population < 0) {
			i = 0;
			while (population < 0 && i < people.length - 1) {
				if (people[i].personTF == true) {						//사람이니
					if (people[i].jobName == "Homeless") {				//노숙자니
						for (j = 0; j < product.length; j++) {			// 그럼 찾아라
							if (product[j].name == "homeless") {		//노숙자를 
								product[j] = new Product();			//노숙자를 지워라
								break;
							}
						}
					}
					people[i] = new Person();
					population++;
				}
				i++;
			};
		}
	}
}
