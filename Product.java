package TeamProject;


import javax.swing.ImageIcon;

public class Product implements Comparable<Product>{
	Boolean productTF = false;
	String name = "";
	ImageIcon icon = null;
	int price = 0;
	int locationX = 0;
	int locationY = 0;
	int residentsIn = 0;
	double happyIn = 0.0;
	static Product[] product = new Product[100];
	static Product[] market = new Product[8];
	
	public static Product[] getProduct(){
		return Product.product;
	}
	public static void setProduct(Product product[]) {
		Product.product = product;
	}
	public static Product[] getMarket(){
		return Product.market;
	}
	public static void setMarket(Product[] market) {
		Product.market = market;
	}
	
	public int randomX(){
		int x = (int) Math.round(Math.random()*850+50);
		x = x-(x%30);
		return x;
	}

	public int randomXY(int x){
		int maxy = 0, miny=0, y=0;
		if (x<=500.0) {
			 maxy = (int) (240.0+((x-20.0)*0.5/2));
			 miny = (int) (240.0-((x-20.0)*0.5/2));
			 
		}else if (x>500.0) {
			maxy = (int) (240.0+((930.0-x)*0.5/2));
			miny = (int) (240.0-((930.0-x)*0.5/2));
		}
		y = (int) Math.round(Math.random()*(maxy-miny)+miny);
		y = y - (y%30);
		return y;	
	}

	public Product() {
		this.productTF = false;
		this.name = "";
		this.residentsIn = 0;
		this.happyIn = 0.0;
		this.price = 0;
		this.icon = null;
		this.locationX = 0;
		this.locationY = 0;
	}

	public Product(Product[] product, String name) {
		if (name == "product") {
			for (int i = 0; i < product.length; i++) {
				if (i < 5) {
					product[i] = makeItem(product[i], 0);
					product[i].locationX = randomX();
					product[i].locationY = randomXY(product[i].locationX);
				} else {
					product[i] = new Product();
				}
			}
		} else if (name == "market") {
			for (int i = 0; i < 8; i++) {
				product[i] = makeItem(product[i], i);
			}
		}
	}

	public Product(Boolean productTF, String name, int residentsIn, double happyIn, int price, ImageIcon icon) {
		this.productTF = productTF;
		this.name = name;
		this.residentsIn = residentsIn;
		this.happyIn = happyIn;
		this.price = price;
		this.icon = icon;
	}
	
	public Product makeItem(Product p, int i) {
		switch(i) {
		case(0): p = new Product(true, "house1", 1, 0.0, 100, new ImageIcon("img/house1.png"));	break;
		case(1): p = new Product(true, "house2", 2, 0.0, 180, new ImageIcon("img/house2.png"));	break;
		case(2): p = new Product(true, "house4", 4, 0.0, 360, new ImageIcon("img/house4.png"));	break;
		case(3): p = new Product(true, "house8", 8, 0.0, 720, new ImageIcon("img/house8.png"));	break;
		case(4): p = new Product(true, "flower", 0, 0.01, 45, new ImageIcon("img/flower.png"));	break;
		case(5): p = new Product(true, "tree", 0, 0.02, 100, new ImageIcon("img/tree.png"));	break;
		case(6): p = new Product(true, "bench", 0, 0.1, 550, new ImageIcon("img/bench.png"));	break;
		case(7): p = new Product(true, "fountain", 0, 0.2, 1000, new ImageIcon("img/fountain.png"));	break;
		
		}
		return p;
	}
	public Product makeHomeless(Product p) {
		p = new Product(true, "homeless", 0, 0, 0, new ImageIcon("img/homeless.png"));  
		p.locationX = randomX();
		p.locationY = randomXY(p.locationX);
		return p;
	}
	
	
	
	static int sumResidentsIn(Product p[]) {
		int sum = 0;
		for(int i = 0; i <p.length; i++) {
			if (p[i].productTF == true)
				sum += p[i].residentsIn;
		}
		return sum;
	}
	
	static int sumHappy(Product p[]) {
		int sum = 1;
		for(int i = 0; i <p.length; i++) {
			if (p[i].productTF == true)
				sum += p[i].happyIn;
		}
		return sum;
	}
	@Override
	public int compareTo(Product o) {
		return o.locationY - this.locationY;
	}
	
}
