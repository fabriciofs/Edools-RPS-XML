package model.edools;

/**
 * Created by Vitor on 10/11/2015.
 */
public class Item {

	private long id;
	private long amount_to_pay;
	private Product product;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAmount_to_pay() {
		return amount_to_pay;
	}

	public void setAmount_to_pay(long amount_to_pay) {
		this.amount_to_pay = amount_to_pay;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
