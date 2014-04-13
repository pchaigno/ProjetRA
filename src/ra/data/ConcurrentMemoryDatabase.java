package ra.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ConcurrentMemoryDatabase extends MemoryDatabase {
	protected int nbThreads;
	
	/**
	 * Thread which computes the support.
	 * @see calcSupport
	 */
	class CalcSupportThread extends Thread {
		private List<Itemset> itemsets;
		private int firstItemset;
		private int lastItemset;
		
		/**
		 * Constructor
		 * @param itemsets Itemsets whose support needs to be computed.
		 * @param firstItemset The first transaction to read.
		 * @param nbItemsets The number of transaction to read in this thread.
		 */
		public CalcSupportThread(List<Itemset> itemsets, int firstItemset, int nbItemsets) {
			this.itemsets = itemsets;
			this.firstItemset = firstItemset;
			this.lastItemset = firstItemset + nbItemsets - 1;
		}
		
		@Override
		public void run() {
			Itemset itemset;
			for(int i=this.firstItemset; i<=this.lastItemset; i++) {
				itemset = this.itemsets.get(i);
				for(Transaction transaction: transactions) {
					if(transaction.contains(itemset)) {
						itemset.incrementSupport();
					}
				}
			}
		}
	}
	
	/**
	 * Thread which computes partially the support.
	 * @see calcSupportIncomplete
	 */
	class CalcSupportIncompleteThread extends Thread {
		List<Itemset> itemsets;
		private int minSupport;
		private int firstItemset;
		private int lastItemset;
		
		/**
		 * Constructor
		 * @param itemsets Itemsets whose support needs to be updated.
		 * @param minSupport The minimum support.
		 * @param firstItemset The first itemset to update.
		 * @param nbItemsets The number of itemsets to update.
		 */
		public CalcSupportIncompleteThread(List<Itemset> itemsets, int minSupport, int firstItemset, int nbItemsets) {
			this.itemsets = itemsets;
			this.minSupport = minSupport;
			this.firstItemset = firstItemset;
			this.lastItemset = firstItemset + nbItemsets - 1;
		}

		@Override
		public void run() {
			Itemset itemset;
			for(int i=this.firstItemset; i<=this.lastItemset; i++) {
				itemset = this.itemsets.get(i);
				for(int j=0; j<transactions.size(); j++) {
					// Checks if the itemset isn't already known as frequent.
					if(itemset.stopPoint == 0) {
						if(transactions.get(j).contains(itemset)) {
							itemset.incrementSupport();
							if(itemset.getSupport() >= this.minSupport) {
								// Save the last transaction read to update later.
								itemset.stopPoint = j;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Thread which updates the support.
	 * @see updateSupport
	 */
	class UpdateSupportThread extends Thread {
		private List<Itemset> itemsets;
		private int firstItemset;
		private int lastItemset;
		
		/**
		 * Constructor
		 * @param itemsets Itemsets whose support needs to be updated.
		 * @param firstItemset The first itemset to update.
		 * @param nbItemset The number of itemsets to update.
		 */
		public UpdateSupportThread(List<Itemset> itemsets, int firstItemset, int nbItemset) {
			this.itemsets = itemsets;
			this.firstItemset = firstItemset;
			this.lastItemset = firstItemset + nbItemset - 1;
		}
		
		@Override
		public void run() {
			Itemset itemset;
			for(int i=this.firstItemset; i<=this.lastItemset; i++) {
				itemset = this.itemsets.get(i);
				for(int j=itemset.stopPoint+1; j<transactions.size(); j++) {
					if(transactions.get(j).contains(itemset)) {
						itemset.incrementSupport();
					}
				}
			}
		}
	}

	/**
	 * Constructor
	 * @param file Database file containing the transactions.
	 * @param nbThreads Number of parallel threads to use.
	 * The best option is to set it to the number of core of the computer.
	 */
	public ConcurrentMemoryDatabase(File file, int nbThreads) {
		super(file);
		this.nbThreads = nbThreads;
	}
	
	@Override
	public void calcSupport(List<Itemset> itemsets) {
		int nbItemsetsPerThread = itemsets.size() / this.nbThreads;
		int nbRemainingItemsets = itemsets.size() - nbItemsetsPerThread * this.nbThreads;
		List<CalcSupportThread> threads = new ArrayList<CalcSupportThread>();
		
		// The first thread will get a bit more transactions (nbTransactions not always divisible by nbThreads):
		CalcSupportThread firstThread = new CalcSupportThread(itemsets, 0, nbRemainingItemsets + nbItemsetsPerThread);
		threads.add(firstThread);
		firstThread.start();
		for(int i=nbRemainingItemsets+nbItemsetsPerThread; i<itemsets.size(); i+=nbItemsetsPerThread) {
			CalcSupportThread thread = new CalcSupportThread(itemsets, i, nbItemsetsPerThread);
			threads.add(thread);
			thread.start();
		}
		
		// Wait for all the threads to end:
		for(CalcSupportThread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	@Override
	public void updateSupport(List<Itemset> itemsets) {
		int nbItemsetsPerThread = itemsets.size() / this.nbThreads;
		int nbRemainingItemsets = itemsets.size() - nbItemsetsPerThread * this.nbThreads;
		List<UpdateSupportThread> threads = new ArrayList<UpdateSupportThread>();
		
		// The first thread will get a bit more itemsets (nbItemsets not always divisible by nbThreads):
		UpdateSupportThread firstThread = new UpdateSupportThread(itemsets, 0, nbRemainingItemsets + nbItemsetsPerThread);
		threads.add(firstThread);
		firstThread.start();
		for(int i=nbRemainingItemsets+nbItemsetsPerThread; i<itemsets.size(); i+=nbItemsetsPerThread) {
			UpdateSupportThread thread = new UpdateSupportThread(itemsets, i, nbItemsetsPerThread);
			threads.add(thread);
			thread.start();
		}
		
		// Wait for all the threads to end:
		for(UpdateSupportThread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	/**
	 * We must redefine withMinSupport because the algorithm for calcSupportIncomplete isn't the same as in MemoryDatabase.
	 * In this algorithm the un-frequent itemsets are not removed while reading the transactions.
	 */
	@Override
	public List<Itemset> withMinSupport(List<Itemset> itemsets, int minSupport, boolean completeSupportCalc) {
		if(completeSupportCalc) {
			this.calcSupport(itemsets);
		} else {
			this.calcSupportIncomplete(itemsets, minSupport);
		}
		for(int i=0; i<itemsets.size(); i++) {
			if(itemsets.get(i).getSupport() < minSupport) {
				itemsets.remove(i);
				i--;
			}
		}
		return itemsets;
	}
	
	@Override
	protected List<Itemset> calcSupportIncomplete(List<Itemset> itemsets, int minSupport) {
		int nbItemsetsPerThread = itemsets.size() / this.nbThreads;
		int nbRemainingItemsets = itemsets.size() - nbItemsetsPerThread * this.nbThreads;
		List<CalcSupportIncompleteThread> threads = new ArrayList<CalcSupportIncompleteThread>();
		
		// The first thread will get a bit more transactions (nbTransactions not always divisible by nbThreads):
		CalcSupportIncompleteThread firstThread = new CalcSupportIncompleteThread(itemsets, minSupport, 0, nbRemainingItemsets + nbItemsetsPerThread);
		threads.add(firstThread);
		firstThread.start();
		for(int i=nbRemainingItemsets+nbItemsetsPerThread; i<itemsets.size(); i+=nbItemsetsPerThread) {
			CalcSupportIncompleteThread thread = new CalcSupportIncompleteThread(itemsets, minSupport, i, nbItemsetsPerThread);
			threads.add(thread);
			thread.start();
		}
		
		// Wait for all the threads to end:
		for(CalcSupportIncompleteThread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
		
		return itemsets;
	}
}
