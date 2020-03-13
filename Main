package TeamProject;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String args[]) {
	
		Graphic gs = new Graphic();
		Village village = new Village();
		
		gs.getVillage(village);
		
		Person[] People = new Person[100]; 								// 사람 객체 생성, 초기화
		for (int i = 0; i < People.length; i++) {
			People[i] = new Person();
			if (i < 5) {
				People[i] = People[i].changeJob(People[i], i%5);
			}
		}
		Person.setPerson(People);
		
		Product market[] = new Product[8];								// 상점생성
		new Product(market, "market");
		Product.setMarket(market);
		
		Product product[] = new Product[100];							// 보유상품객체
		new Product(product, "product");
		Product.setProduct(product);

		
		Timer timerHappy = new Timer(); 								// 행복도체크
		TimerTask timerTaskHappy = new TimerTask() {
			public void run() {
				village.populationCount(People, product);
				village.happyAve(People, product);

			}
		};
		Timer timerPlum = new Timer();									 // 플럼걷기
		TimerTask timerTaskPlum = new TimerTask() {
			public void run() {
				village.plumIncrease(People, product);
			}
		};
		Timer timerPopulation = new Timer();							// 인구증가
		TimerTask timerTaskPopulation = new TimerTask() {
			public void run() {
				village.populationChange(People, product);
			}
		};
		timerHappy.schedule(timerTaskHappy, 0, 1000);
		timerPlum.schedule(timerTaskPlum, 1, 5000);
		timerPopulation.schedule(timerTaskPopulation, 1000*30, 1000*30);
		
		if (Village.playType == "end") {
			
			timerTaskHappy.cancel();
			timerTaskPlum.cancel();
			timerTaskPopulation.cancel();
			
		}
	}

}
