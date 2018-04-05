package com.txmq.exo.persistence.transactions;

import com.txmq.exo.config.ExoConfig;
import com.txmq.exo.core.ExoPlatformLocator;
import com.txmq.exo.core.ExoState;
import com.txmq.exo.messaging.ExoMessage;
import com.txmq.exo.messaging.ExoTransactionType;
import com.txmq.exo.transactionrouter.ExoTransaction;

public class PersistenceTransactions {

	@ExoTransaction(ExoTransactionType.SHUTDOWN)
	public ExoMessage shutdown(ExoMessage message, ExoState state) {
		//If we have a block logger, then ask it to flush to the chain.
		if (ExoPlatformLocator.getBlockLogger() != null) {
			ExoPlatformLocator.shutdown();	
			ExoPlatformLocator.getBlockLogger().flush(ExoPlatformLocator.getPlatform().getAddress().getSelfName());
			System.out.println("It is now safe to shut down.");
		}
		
		return message;
	}
	
	@ExoTransaction(ExoTransactionType.RECOVER_STATE)
	public ExoMessage recoverState(ExoMessage message, ExoState state) {
		return message;
	}
}