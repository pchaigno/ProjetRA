package ra.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ra.algo.Itemset;

public class ConcurrentMemoryDatabase extends MemoryDatabase {
	protected int nbThreads;
	
	/**
	 * Thread which compute the support.
	 * @see calcSupport
	 */
	class CalcSupportThread extends Thread {
		private List<Itemset> itemsets;
		private int firstTransaction;
		private int lastTransaction;
		
		/**
		 * Constructor
		 * @param itemsets Itemsets whose support need to be computed.
		 * @param firstTransaction The first transaction to read.
		 * @param nbTransactions The number of transaction to read in this thread.
		 */
		public CalcSupportThread(List<Itemset> itemsets, int firstTransaction, int nbTransactions) {
			this.itemsets = itemsets;
			this.firstTransaction = firstTransaction;
			this.lastTransaction = firstTransaction + nbTransactions - 1;
		}
		
		@Override
		public void run() {
			for(int i=this.firstTransaction; i<=this.lastTransaction; i++) {
				for(Itemset itemset: this.itemsets) {
					if(transactions.get(i).contains(itemset)) {
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
		int nbTransactionsPerThread = this.transactions.size() / this.nbThreads;
		int nbRemainingTransactions = this.transactions.size() - nbTransactionsPerThread * this.nbThreads;
		List<CalcSupportThread> threads = new ArrayList<CalcSupportThread>();
		
		// The first thread will get a bit more transactions (nbTransactions not always divisible by nbThreads):
		CalcSupportThread firstThread = new CalcSupportThread(itemsets, 0, nbRemainingTransactions + nbTransactionsPerThread);
		threads.add(firstThread);
		firstThread.start();
		for(int i=nbRemainingTransactions+nbTransactionsPerThread; i<this.transactions.size(); i+=nbTransactionsPerThread) {
			CalcSupportThread thread = new CalcSupportThread(itemsets, i, nbTransactionsPerThread);
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
}
