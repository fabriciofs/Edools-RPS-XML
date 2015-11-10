package model.edools;

/**
 * Created by Vitor on 10/11/2015.
 */
public class Payment {

	public long id;
	public long amount;
	public Customer customer;
	public Order order;
	public String created_at;
	public String updated_at;

}
