package protocol.impl;

import java.util.HashMap;

import controller.Application;
import model.api.UserSyncManager;
import model.entity.ElGamalKey;
import model.entity.User;
import model.syncManager.UserSyncManagerImpl;
import protocol.api.Contract;
import protocol.api.Establisher;
import protocol.api.EstablisherListener;
import protocol.api.Status;
import protocol.api.Wish;
import protocol.impl.sigma.SigmaEstablisher;
import rest.api.Authentifier;
import protocol.impl.contract.ElGamalContract;

public class SigmaEstablisherAdapter implements Establisher {

	private SigmaEstablisher establisher;
	private ElGamalContract contract;
	private HashMap<ElGamalKey, String> uris;
	private ElGamalKey key;
	
	
	/**
	 * Constructor
	 * @param token : necessary to get the current user Keys
	 * @param u : matching uris and keys
	 */
	public SigmaEstablisherAdapter(String token, HashMap<ElGamalKey, String> u){
		uris = u;
		
		Authentifier auth = Application.getInstance().getAuth();
		UserSyncManager users = new UserSyncManagerImpl();
		User currentUser = users.getUser(auth.getLogin(token), auth.getPassword(token));
		key = currentUser.getKey();
	}
	
	/**
	 * Initialize the contract with
	 */
	@Override
	public void initialize(Contract<?, ?, ?, ?> c) {
		contract = (ElGamalContract) c;
		
		establisher = new SigmaEstablisher(contract, key, uris);
	}
	
	/**
	 * When pressing "Sign" button
	 */
	@Override
	public void start() {
		establisher.start();
	}
	
	/**
	 * @return contract being signed 
	 */
	@Override
	public Contract<?, ?, ?, ?> getContract() {
		this.contract = establisher.getContract();
		return contract;
	}
	
	/**
	 * Get the owner of this establisher's wish
	 */
	@Override
	public Wish getWish() {
		return contract.getWish();
	}

	/**
	 * Set the owner's wish, if it is acceptance -> start protocol
	 */
	@Override
	public void setWish(Wish w) {
		contract.setWish(w);
		if (w.equals(Wish.ACCEPT)){
			this.start();
		} else if (w.equals(Wish.REFUSE)){
			establisher.resolve();
		}
	}

	/**
	 * Get the current status of the signing protocol
	 */
	@Override
	public Status getStatus() {
		return establisher.status;
	}

	/**
	 * Put a listener on the status change of the establisher
	 */
	@Override
	public void addListener(EstablisherListener l) {
		establisher.listeners.add(l);
	}

	/**
	 * Function called when changing the status to notify the listeners
	 */
	@Override
	public void notifyListeners() {
		establisher.notifyListeners();
	}

}
