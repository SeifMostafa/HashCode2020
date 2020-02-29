package Drive;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import model.Book;
import model.Library;
import model.Score;

public class BookScanningTask {
	static int time = 7;
	public static ArrayList<Book> allBooks;
	public static ArrayList<Library> libraries;
	static String DIRECTORY_PATH = "/home/azizax/Downloads/HASHCODE/2020/FOR_SUBMISSION4/";
	static String[] inputFileNames = { "a_example.txt", "b_read_on.txt", "c_incunabula.txt", "d_tough_choices.txt",
			"e_so_many_books.txt", "f_libraries_of_the_world.txt" };

	public static void main(String[] args) throws IOException, InterruptedException {

//	
//		for(String path: args) {
//			// File text = new File(args[0]); // whole path
//		}

		for (String fileName : inputFileNames) {
			int allBooksLen = 6;
			int allLibs = 2;

			int MAX_SCORE = 0;
			int timeRequiedToSignUpAllLibs = 0;

			libraries = new ArrayList<Library>();
			allBooks = new ArrayList<Book>();

			File text = new File(DIRECTORY_PATH + fileName);

			Scanner scnr = new Scanner(text);
			allBooksLen = Integer.valueOf(scnr.next());
			allLibs = Integer.valueOf(scnr.next());
			time = Integer.valueOf(scnr.next());

			for (int i = 0; i < allBooksLen; i++) {
				int score = Integer.valueOf(scnr.next());
				allBooks.add(new Book(score, i));
				MAX_SCORE += score;
			}

			for (int i = 0; i < allLibs; i++) {
				int bookInLibLen = Integer.valueOf(scnr.next());
				int signUpProc = Integer.valueOf(scnr.next());
				int shipPerDay = Integer.valueOf(scnr.next());
				ArrayList<Book> booksIn = new ArrayList<Book>();

				for (int ii = 0; ii < bookInLibLen; ii++) {
					int idx = Integer.valueOf(scnr.next());
					booksIn.add(new Book(idx));
				}
				libraries.add(new Library(booksIn, shipPerDay, signUpProc, i));
				timeRequiedToSignUpAllLibs += signUpProc;
			}
			scnr.close();
			///////////////////////////////// start solving ///////////////////////////////
			System.out.println("Max score could be reached: " + MAX_SCORE);
			System.out.println("time required to signup all libs: " + timeRequiedToSignUpAllLibs);

//			ArrayList<Score> scoresCollected = new ArrayList<Score>();
//			new Thread() {
//				@Override
//				public void run() {
//					Score score = getSumScoreBySignUpSort("getSumScoreBySignUpSort",libraries,time);
//					System.out.println(score.toString());
//				//	printAns(score.getAnswerString(), DIRECTORY_PATH+"SOL_getSumScoreBySignUpSort_"+fileName);
//					scoresCollected.add(score);
//				}
//
//			}.start();
//			new Thread() {
//				@Override
//				public void run() {
//					Score score = getSumScoreByScoreSort("getSumScoreByScoreSort",libraries,time);
//					System.out.println(score.toString());
//				//	printAns(score.getAnswerString(), DIRECTORY_PATH+"SOL_getSumScoreByScoreSort_"+fileName);
//					scoresCollected.add(score);
//				}
//
//			}.start();
//			new Thread() {
//				@Override
//				public void run() {
//					Score score = getSumScoreByShipPerDaySort("getSumScoreByShipPerDaySort",libraries,time);
//					System.out.println(score.toString());
//					//printAns(score.getAnswerString(), DIRECTORY_PATH+"SOL_getSumScoreByShipPerDaySort_"+fileName);
//					scoresCollected.add(score);
//				}
//
//			}.start();
//
//			new Thread() {
//				@Override
//				public void run() {
//					Score score = getSumScoreUniqueBooksOrder("getSumScoreUniqueBooksOrder",allBooks,time);
//					System.out.println(score.toString());
//				//	printAns(score.getAnswerString(), DIRECTORY_PATH+"SOL_getSumScoreUniqueBooksOrder_"+fileName);
//					scoresCollected.add(score);
//				}
//
//			}.start();
//		
//			while (scoresCollected.size() < 4) {
//			}
//			Collections.sort(scoresCollected, bestSolComparator);

			new Thread() {
				@Override
				public void run() {
					ArrayList<Library> ourOwnLibs = (ArrayList<Library>) libraries.clone();
					Collections.sort(ourOwnLibs, comparatorCompareBysignUpProccess);
					Integer ourTime = time;
					Score score = new Score();
					score.setMethodologyName("getSumScoreBySignUpSort");
					calculateSignUpProccessTimeBased(ourOwnLibs, ourTime, score);
					System.out.println(score.toString());
					score.setAnswerString(score.numberOfLibsSignedUp + "\n".concat(score.getAnswerString()));

					printAns(score.getAnswerString(), DIRECTORY_PATH + "SOL_getSumScoreUniqueBooksOrder_" + fileName);
					// scoresCollected.add(score);
				}

			}.run();

			// printAns(scoresCollected.get(0).getAnswerString(),
			// DIRECTORY_PATH+"BEST_REACHED_SOL_"+fileName);
		}
	}

	public synchronized static Score getSumScoreBySignUpSort(String methodologyName, ArrayList<Library> orgLibsList,
			int time) {
		ArrayList<Library> ourOwnLibs = (ArrayList<Library>) orgLibsList.clone();
		Collections.sort(ourOwnLibs, comparatorCompareBysignUpProccess);
		// System.out.println("Get score after sorting by signup time starting..");
		Score score = getSumScoreOf(ourOwnLibs, time);
		score.setMethodologyName(methodologyName);
		return score;

	}

	public static Score getSumScoreByScoreSort(String methodologyName, ArrayList<Library> orgLibsList, int time) {
		ArrayList<Library> ourOwnLibs = (ArrayList<Library>) orgLibsList.clone();
		Collections.sort(ourOwnLibs, comparatorCompareByscoreSumOfBooks);
		// System.out.println("Get score after sorting by score starting..");
		Score score = getSumScoreOf(ourOwnLibs, time);
		score.setMethodologyName(methodologyName);
		return score;

	}

	public synchronized static Score getSumScoreByShipPerDaySort(String methodologyName, ArrayList<Library> orgLibsList,
			int time) {

		ArrayList<Library> ourOwnLibs = (ArrayList<Library>) orgLibsList.clone();
		Collections.sort(ourOwnLibs, comparatorCompareByshipPerDay);
		// System.out.println("Get score after sorting by #books could be shipped per
		// day starting..");
		Score score = getSumScoreOf(ourOwnLibs, time);
		score.setMethodologyName(methodologyName);
		return score;

	}

	public synchronized static Score getSumScoreUniqueBooksOrder(String methodologyName, ArrayList<Book> orgBooksList,
			int time) {
		ArrayList<Book> ourOwnBooks = (ArrayList<Book>) orgBooksList.clone();
		Collections.sort(ourOwnBooks, bookComparatorByNumberOfLibsContain);
		ArrayList<Library> ourOwnLibs = new ArrayList<Library>();

		for (Book b : ourOwnBooks) {
			if (!b.libsContainBook.isEmpty() && !ourOwnLibs.contains(b.libsContainBook.get(0)))
				ourOwnLibs.add(b.libsContainBook.get(0));
		}
		// System.out.println("Get score after sorting all books by number of libs
		// contain.");
		// System.out.println("ourOwnLibs: " + ourOwnLibs.size());

		Score score = getSumScoreOf(ourOwnLibs, time);
		score.setMethodologyName(methodologyName);
		return score;

	}

	public synchronized static Score getSumScoreOf(ArrayList<Library> ourOwnLibs, int time) {
		int ourTime = time;
		int ourScore = 0;
		int numOfLibsSendBooks = 0;
		String answer = "";
		for (int i = 0; i < ourOwnLibs.size() && ourTime > 0; i++) {
			Library lib = ourOwnLibs.get(i);
			if (!lib.books.isEmpty() && lib.scoreSumOfBooks > 0) {
				List<Book> booksToBeScanned1;
				answer += lib.idx;
				if (ourTime - (lib.books.size() / lib.shipPerDay) >= 0) {
					ourTime -= (lib.books.size() / lib.shipPerDay);
					ourScore += lib.scoreSumOfBooks;
					booksToBeScanned1 = lib.books;
				} else {
					Collections.sort(lib.books, BookComparatorByScore);
					booksToBeScanned1 = lib.books.subList(0, lib.shipPerDay * ourTime);
					for (Book b : booksToBeScanned1) {
						ourScore += b.score;

					}

					ourTime = 0;
				}
				answer += " " + booksToBeScanned1.size() + "\n";

				for (int k = 0; k < booksToBeScanned1.size(); k++) {
					Book scannedBook = booksToBeScanned1.get(k);
					answer += scannedBook.idx + " ";
					for (int j = i + 1; j < ourOwnLibs.size(); j++) {
						Library otherLib = ourOwnLibs.get(j);
						int socreDetuct = 0;
						Stream<Book> scannedBooksFound = otherLib.books.stream()
								.filter(b -> (b.idx == scannedBook.idx));
						Iterator<Book> scannedBookIter = scannedBooksFound.iterator();
						ArrayList<Book> booksToBeRemoved = new ArrayList<Book>();
						while (scannedBookIter.hasNext()) {
							Book b = scannedBookIter.next();
							socreDetuct += b.score;
							booksToBeRemoved.add(b);

						}

						otherLib.scoreSumOfBooks = otherLib.scoreSumOfBooks - socreDetuct;
						otherLib.books.removeAll(booksToBeRemoved);
					}
				}
				answer = answer.substring(0, answer.length() - 1) + "\n";
				numOfLibsSendBooks++;
			}
		}
		answer = answer.substring(0, answer.length() - 1);
		answer = numOfLibsSendBooks + "\n".concat(answer);

		return new Score(answer, ourScore);
	}

	static Comparator<Library> comparatorCompareByscoreSumOfBooks = new Comparator<Library>() {
		@Override
		public int compare(Library o1, Library o2) {
			return o2.scoreSumOfBooks.compareTo(o1.scoreSumOfBooks);
		}
	};
	static Comparator<Library> comparatorCompareByshipPerDay = new Comparator<Library>() {
		@Override
		public int compare(Library o1, Library o2) {
			return o2.shipPerDay.compareTo(o1.shipPerDay);
		}
	};
	static Comparator<Library> comparatorCompareBysignUpProccess = new Comparator<Library>() {
		@Override
		public int compare(Library o1, Library o2) {
			return o1.signUpProccess.compareTo(o2.signUpProccess);
		}
	};

	static Comparator<Book> BookComparatorByScore = new Comparator<Book>() {
		@Override
		public int compare(Book o1, Book o2) {
			return o2.score.compareTo(o1.score);
		}
	};

	static Comparator<Book> bookComparatorByNumberOfLibsContain = new Comparator<Book>() {
		@Override
		public int compare(Book o1, Book o2) {
			return ((Integer) o1.libsContainBook.size()).compareTo(((Integer) o2.libsContainBook.size()));
		}
	};
	static Comparator<Score> bestSolComparator = new Comparator<Score>() {
		@Override
		public int compare(Score o1, Score o2) {
			return ((Integer) o2.getMaxScoreReached()).compareTo(((Integer) o1.getMaxScoreReached()));
		}
	};

	public static List<Book> accumulateLib(Library lib, Integer ourTime, Score initScore) {
		List<Book> booksToBeScanned1 = null;

		if (ourTime - (lib.books.size() / lib.shipPerDay) >= 0) {
			ourTime -= (lib.books.size() / lib.shipPerDay);
			initScore.setMaxScoreReached(initScore.getMaxScoreReached() + lib.scoreSumOfBooks);
			booksToBeScanned1 = lib.books;
		} else {
			Collections.sort(lib.books, BookComparatorByScore);
			booksToBeScanned1 = lib.books.subList(0, lib.shipPerDay * ourTime);
			for (Book b : booksToBeScanned1) {
				initScore.setMaxScoreReached(initScore.getMaxScoreReached() + b.score);
			}

			ourTime = 0;
		}
		return booksToBeScanned1;
	}

	public static ArrayList<Library> removeLibsContain(ArrayList<Library> libs, List<Book> scannedBooks1, Score score) {
		for (int k = 0; k < scannedBooks1.size(); k++) {
			Book scannedBook = scannedBooks1.get(k);
			score.setAnswerString(score.getAnswerString() + scannedBook.idx + " ");
			for (int j = 1; j < libs.size(); j++) {
				Library otherLib = libs.get(j);
				int socreDetuct = 0;
				Stream<Book> scannedBooksFound = otherLib.books.stream().filter(b -> (b.idx == scannedBook.idx));
				Iterator<Book> scannedBookIter = scannedBooksFound.iterator();
				ArrayList<Book> booksToBeRemoved = new ArrayList<Book>();
				while (scannedBookIter.hasNext()) {
					Book b = scannedBookIter.next();
					socreDetuct += b.score;
					booksToBeRemoved.add(b);

				}

				otherLib.scoreSumOfBooks = otherLib.scoreSumOfBooks - socreDetuct;
				otherLib.books.removeAll(booksToBeRemoved);
			}
		}
		score.setAnswerString(score.getAnswerString() + "\n");

		return libs;
	}

	public static Score calculateSignUpProccessTimeBased(ArrayList<Library> orgLibs, Integer availableTime,
			Score initScore) {
		while (!orgLibs.isEmpty()) {

			Library lib = orgLibs.get(0);

			if (!lib.books.isEmpty() && lib.scoreSumOfBooks > 0) {
				initScore.setAnswerString(initScore.getAnswerString() + lib.idx);
				List<Book> booksToBeScanned = accumulateLib(lib, availableTime, initScore);
				if (booksToBeScanned != null && !booksToBeScanned.isEmpty()) {
					initScore.setAnswerString(initScore.getAnswerString() + " " + booksToBeScanned.size() + "\n");
					orgLibs = removeLibsContain(orgLibs, booksToBeScanned, initScore);
					initScore.setAnswerString(
							initScore.getAnswerString().substring(0, initScore.getAnswerString().length() - 1) + "\n");
					initScore.numberOfLibsSignedUp++;
				}
			}
			orgLibs.remove(0);
			Collections.sort(orgLibs, comparatorCompareBysignUpProccess);
		}
		return initScore;
	}

	private static void printAns(String answer, String filename) {
		try {
			FileWriter myWriter;
			myWriter = new FileWriter(filename);
			myWriter.write(answer);
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
