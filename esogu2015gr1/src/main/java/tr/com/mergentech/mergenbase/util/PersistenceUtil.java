package tr.com.mergentech.mergenbase.util;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class PersistenceUtil {

	/**
	 * 
	 * parametre olarak verilmiş olan criteria nesnesine aliasYapilacakDeger
	 * olarak verilen string ifadeyi uygun bir şekilde alias olarak
	 * yerleştiriyor.
	 * 
	 * @param criteria
	 *            , aliasların atanacağı criteria nesnesi
	 * 
	 * @param aliasYapilacakDeger
	 *            , aliaslar olarak atanılması istenen bilgileri sahip string
	 *            katarı
	 * 
	 * @author Musa YUVACI
	 * 
	 * @version 1.0 07.07.2011
	 * 
	 */
	public static void createAlias(Criteria criteria, String aliasYapilacakDeger) {

		/*
		 * Hibernate criteria api de sorgulama yaparken nesnelerin altindaki
		 * diger nesnelerin elemenlarina ulasmak icin alias tanımlaması
		 * yapılması gerekiyor. Bu sebeple burada gelen aliasYapilacakDeger
		 * bilgisine gore alias tanımlaması yapılıyor
		 * 
		 * Oncelikle gelen aliasYapilacakDeger bilgisini ayrıstırıyoruz.
		 * Sonrasında da aliasYapilacakDeger ile gelen bilgilere gore alias
		 * ataması yapıyoruz sonrasında da criteria ya ona gore order ekliyoruz
		 */
		String[] ayrilmisSortingListesi = aliasYapilacakDeger.split("\\.");

		/*
		 * Burada kontrol edilmesi gereken bir nokta daha var. Eger gelen
		 * sortingField bilgisi sadece bir nesne ve degisken iceriyorsa alias
		 * atamıyoruz.
		 */
		if (ayrilmisSortingListesi.length > 1) {

			for (int i = 0; i < (ayrilmisSortingListesi.length - 1); i++) {

				if (i == 0) {

					criteria.createAlias(ayrilmisSortingListesi[i], ayrilmisSortingListesi[i],Criteria.LEFT_JOIN);

				} else {

					criteria.createAlias(ayrilmisSortingListesi[i - 1] + "." + ayrilmisSortingListesi[i], ayrilmisSortingListesi[i],Criteria.LEFT_JOIN);
				}
			}
		}
	}

	/**
	 * 
	 * parametre olarak verilmiş olan criteria nesnesine orderingField olarak
	 * verilen string ifadeyi uygun bir şekilde alias olarak yerleştirip,
	 * sonrasında da order işlemi için oluşturulan aliaslardan kullanılması
	 * gerekeni criteria nesnesine atar. Order işleminin tipide (ascending ,
	 * descending) parametre olarak verilen ASC ile belirlenir.
	 * 
	 * @param criteria
	 *            orderın atanacağı nesne.
	 * 
	 * @param orderingField
	 *            order olarak atanacak nesne (Fakat uygun değilse aliaslara
	 *            bölünecek)
	 * 
	 * @param ASC
	 *            yapılacak olan orderın tipini belirliyor.True ise ascending ,
	 *            false ise dexcending sıralama gerçekleştiriliyor.
	 * 
	 * @author Musa YUVACI
	 * 
	 * @version 1.0 07.07.2011
	 * 
	 */
	public static void createAliasForOrdering(Criteria criteria, String orderingField, boolean ASC) {

		/*
		 * Hibernate criteria api de sorgulama yaparken nesnelerin altindaki
		 * diger nesnelerin elemenlarina ulasmak icin alias tanımlaması
		 * yapılması gerekiyor. Bu sebeple burada gelen sortingField bilgisine
		 * gore alias tanımlaması yapılıyor
		 * 
		 * Oncelikle gelen sortingField bilgisini ayrıstırıyoruz. Sonrasında da
		 * sortingField adresi ile gelen bilgilere gore alias atamas� yap�yoruz
		 * sonrasında da criteria ya ona gore order ekliyoruz
		 */
		String[] ayrilmisSortingListesi = orderingField.split("\\.");

		/*
		 * Burada kontrol edilmesi gereken bir nokta daha var. Eger gelen
		 * sortingField bilgisi sadece bir nesne ve degisken iceriyorsa alias
		 * atamıyoruz.
		 */
		if (ayrilmisSortingListesi.length > 1) {

			for (int i = 0; i < (ayrilmisSortingListesi.length - 1); i++) {

				if (i == 0) {

					criteria.createAlias(ayrilmisSortingListesi[i], ayrilmisSortingListesi[i],Criteria.LEFT_JOIN);

				} else {

					criteria.createAlias(ayrilmisSortingListesi[i - 1] + "." + ayrilmisSortingListesi[i], ayrilmisSortingListesi[i],Criteria.LEFT_JOIN);
				}
			}

			if (ASC) {

				criteria.addOrder(Order.asc(ayrilmisSortingListesi[ayrilmisSortingListesi.length - 2] + "." + ayrilmisSortingListesi[ayrilmisSortingListesi.length - 1]));

			} else {

				criteria.addOrder(Order.desc(ayrilmisSortingListesi[ayrilmisSortingListesi.length - 2] + "." + ayrilmisSortingListesi[ayrilmisSortingListesi.length - 1]));
			}

		} else {

			if (ASC) {

				criteria.addOrder(Order.asc(orderingField));
			} else {

				criteria.addOrder(Order.desc(orderingField));
			}
		}
	}

	/**
	 * <P>
	 * Parametre olarak verilmiş olan searchingField ve orderingField
	 * alanlarının uygun olanı ile alias işlemi yapılıyor. Ardından da
	 * orderingField olarak verilen ifade order tipine göre criteria nesnesine
	 * order olarak atanıyor.<BR><BR>
	 * 
	 * NOT: Bu metodun yazılma amacı aynı anda birden fazla aliaslama i�lemi
	 * olduğunda bunun doğru bir şekilde yapılabilmesi içindir.<BR><BR>
	 * 
	 * Örneğin :<BR><BR>
	 * 
	 * orderingField --> "klasor.seri.ad" searchingField -->
	 * "klasor.seri.birim.dosyaTasnif.ad", bu durumda searchingField için alias
	 * tanımlaması yapılır bu sırada zaten orderingFieldin de kullanabileceği
	 * aliaslar oluştuğundan bri daha alias tanımlamak zorunda kalınmaz vede
	 * alias duplication olayınında önüne geçilir.<BR><BR>
	 * 
	 * @param criteria
	 *            orderın atanacağı nesne.
	 * 
	 * @param orderingField
	 *            order olarak atanacak nesne (Fakat uygun değilse aliaslara
	 *            bölünecek)
	 *            
	 * @param aliasingField
	 *            alias olarak tanımlanamsı gereken string katarıdır.
	 * 
	 * @param ASC
	 *            yapılacak olan orderın tipini belirliyor.True ise ascending ,
	 *            false ise dexcending sıralama gerçekleştiriliyor.
	 * 
	 * @author Musa YUVACI
	 * 
	 * @version 1.0 07.07.2011
	 * 
	 */
	public static void createAliasForOrderingAndAliasing(Criteria criteria, String orderingField, String aliasingField, boolean ASC) {

		/*
		 * Hibernate criteria api de sorgulama yaparken nesnelerin altindaki
		 * diger nesnelerin elemenlarina ulasmak icin alias tanımlaması
		 * yapılması gerekiyor. Ama aliaslar tek olmalı aynı aliası bir daha
		 * tanımlayamazsınız. Bu sebeple burada gelen sortingField ve
		 * searchingField bilgilerine gore alias tanımlaması yapılıyor
		 * 
		 * Oncelikle gelen sortingField ve searchingField bilgilerininin null
		 * kontrolunu yapıyoruz.
		 */
		if (orderingField != null && aliasingField != null) {

			boolean isAyrilmisSortingListesiBuyuk = false;

			/*
			 * sortingField ve aliasingField bilgilerine ayırıyoruz.
			 */
			String[] ayrilmisSortingListesi = orderingField.split("\\.");
			String[] ayrilmisSearchingListesi = aliasingField.split("\\.");

			String[] aliasList = null;

			/*
			 * Elde ettiğimiz ayrıştırılmış listelerden saha büyük olanını
			 * oluştuduğumuz aliasList dizisine atayıp alias verme işlemine
			 * başlıyoruz.
			 * 
			 * Çünkü gerekli bütün aliasların tanımlanması için daha büyük olan
			 * dizi alınıyor.Ayrıca burada isAyrilmisSortingListesiBuyuk adıyla
			 * tanımladığımız boolean degerede verisini atıyoruz. Bu boolean
			 * degeride sorting için order atarken kullanacağız.
			 */
			if (ayrilmisSortingListesi.length >= ayrilmisSearchingListesi.length) {

				aliasList = ayrilmisSortingListesi;

				isAyrilmisSortingListesiBuyuk = true;

			} else {

				aliasList = ayrilmisSearchingListesi;

				isAyrilmisSortingListesiBuyuk = false;
			}

			/*
			 * Burada kontrol edilmesi gereken bir nokta daha var. Eger gelen
			 * sortingField veya aliasingField bilgisi sadece bir nesne ve
			 * degisken iceriyorsa alias atamıyoruz.
			 */
			if (aliasList.length > 1) {

				/*
				 * Burada alias atamasına başlanıyor. Ve aliasListesine göre
				 * alias ataması yapılıyor.
				 */
				for (int i = 0; i < (aliasList.length - 1); i++) {

					if (i == 0) {

						criteria.createAlias(aliasList[i], aliasList[i],Criteria.LEFT_JOIN);
					} else {

						criteria.createAlias(aliasList[i - 1] + "." + aliasList[i], aliasList[i],Criteria.LEFT_JOIN);
					}
				}

				/*
				 * Burada sorting için order ataması yapılıyor.Öncelikle
				 * sortingin nasıl yapılacağına karar veriliyor.Burdaki if else
				 * blogundaki her şey aynı sadece criteria nesnesine ordera
				 * atarken asc mi desc mi olacağını farklı söylüyoruz.
				 */
				if (ASC) {

					/*
					 * Burda ise isAyrilmisSortingListesiBuyuk değişkeni ile
					 * ayrilmisSortingListesinin büyüklüğü kontrol ediliyor.Eğer
					 * ayrilmisSearchingListesinden daha büyük ise order olarak
					 * aliasListin elemanlarından order ataması yapılıyor.Çünkü
					 * alias tanımlaması sortingField'e göre yapıldı.
					 */
					if (isAyrilmisSortingListesiBuyuk) {

						criteria.addOrder(Order.asc(aliasList[aliasList.length - 2] + "." + aliasList[aliasList.length - 1]));

						/*
						 * Eğer ayrilmisSortingListesinin büyüklüğü
						 * ayrilmisSearchingListesinden daha küçük ise order
						 * için atama ayrilmisSortingListesinin kendisinden
						 * yapılması gerekir. Çünkü alias tanımlama
						 * aliasingField e göre yapılmıştır.
						 */

					} else {

						/*
						 * Buradada ayrilmisSortingListesinin büyüklüğü kontrol
						 * edilmeli. Çünkü order ataması yapılırken sortingField
						 * 1 veya 2 ögeden oluşuyorsa direk sorting field
						 * atanmalıdır.Aliaslık bir iş yoktur.
						 */
						if (ayrilmisSortingListesi.length > 1) {

							criteria.addOrder(Order.asc(ayrilmisSortingListesi[ayrilmisSortingListesi.length - 2] + "." + ayrilmisSortingListesi[ayrilmisSortingListesi.length - 1]));

						} else {

							criteria.addOrder(Order.asc(orderingField));
						}

					}
					/*
					 * Burdaki else blogunun içinde de yukarıdaki if blogu
					 * içindeki işlemlerle aynı iş yapılıyor, farklı olan nokta
					 * ise sortingin descending olarak ayarlanmasıdır.
					 */
				} else {

					if (isAyrilmisSortingListesiBuyuk) {

						criteria.addOrder(Order.desc(aliasList[aliasList.length - 2] + "." + aliasList[aliasList.length - 1]));

					} else {

						if (ayrilmisSortingListesi.length > 2) {

							criteria.addOrder(Order.desc(ayrilmisSortingListesi[ayrilmisSortingListesi.length - 2] + "." + ayrilmisSortingListesi[aliasList.length - 1]));

						} else {

							criteria.addOrder(Order.desc(orderingField));
						}
					}
				}
				/*
				 * Verilmiş olan sortingField ve aliasingField bilgileri için
				 * bir alias atamasına gerek yoktur.Bu yüzden direk olarak
				 * sortingField bilgisi order için criteria nesnesine
				 * atanmıştır.
				 */
			} else {

				if (ASC) {

					criteria.addOrder(Order.asc(orderingField));
				} else {

					criteria.addOrder(Order.desc(orderingField));
				}
			}
		}
	}

	// public static void createAliasForSortingAndSearching(Criteria criteria,
	// String sortingField, String searchingField, boolean ASC) {
	//
	// /*
	// * Hibernate criteria api de sorgulama yaparken nesnelerin altindaki
	// * diger nesnelerin elemenlarina ulasmak icin alias tan�mlamas�
	// * yap�lmas� gerekiyor. Bu sebeple burada gelen sortingField bilgisine
	// * gore alias tan�mlamas� yap�l�yor
	// *
	// * Oncelikle gelen sortingField bilgisini ayr�st�r�yoruz. Sonras�nda da
	// * sortingField adresi ile gelen bilgilere gore alias atamas� yap�yoruz
	// * sonras�nda da criteria ya ona gore order ekliyoruz
	// */
	//
	// boolean isAyrilmisSortingListesiBuyuk = false;
	//
	// String[] ayrilmisSortingListesi = sortingField.split("\\.");
	// String[] ayrilmisSearchingListesi = searchingField.split("\\.");
	//
	// String[] aliasList = null;
	//
	// if (ayrilmisSearchingListesi != null && ayrilmisSortingListesi != null) {
	//
	// if (ayrilmisSortingListesi.length >= ayrilmisSearchingListesi.length) {
	//
	// aliasList = ayrilmisSortingListesi;
	//
	// isAyrilmisSortingListesiBuyuk = true;
	//
	// } else {
	//
	// aliasList = ayrilmisSearchingListesi;
	//
	// isAyrilmisSortingListesiBuyuk = false;
	// }
	//
	// /*
	// * Burada kontrol edilmesi gereken bir nokta daha var. Eger gelen
	// * sortingField bilgisi sadece bir nesne ve degisken iceriyorsa
	// * alias atam�yoruz.
	// */
	// if (aliasList.length > 1) {
	//
	// for (int i = 0; i < (aliasList.length - 1); i++) {
	//
	// if (i == 0) {
	//
	// criteria.createAlias(aliasList[i], aliasList[i]);
	//
	// System.out.println("*****************ALIAS 0*********************");
	// System.out.println(aliasList[i] + " : " + aliasList[i]);
	// System.out.println("********************************************");
	//
	// } else {
	//
	// criteria.createAlias(aliasList[i - 1] + "." + aliasList[i],
	// aliasList[i]);
	//
	// System.out.println("*****************ALIAS " + i
	// +"*********************");
	// System.out.println(aliasList[i - 1] + "." + aliasList[i] + " : " +
	// aliasList[i]);
	// System.out.println("********************************************");
	// }
	// }
	//
	// if (ASC) {
	//
	// if (isAyrilmisSortingListesiBuyuk) {
	//
	// criteria.addOrder(Order.asc(aliasList[aliasList.length - 2] + "." +
	// aliasList[aliasList.length - 1]));
	//
	// System.out.println("*****************SORTING BUYUK ASC *********************");
	// System.out.println(aliasList[aliasList.length - 2] + "." +
	// aliasList[aliasList.length - 1]);
	// System.out.println("********************************************");
	//
	// } else {
	//
	//
	//
	// if(ayrilmisSortingListesi.length > 2) {
	//
	// System.out.println("*****************SORTING KUCUK ASC*********************");
	// System.out.println(ayrilmisSortingListesi[ayrilmisSortingListesi.length -
	// 2] + "." + ayrilmisSortingListesi[ayrilmisSortingListesi.length - 1]);
	// System.out.println("********************************************");
	//
	// criteria.addOrder(Order.asc(ayrilmisSortingListesi[ayrilmisSortingListesi.length
	// - 2] + "." + ayrilmisSortingListesi[ayrilmisSortingListesi.length - 1]));
	//
	// } else {
	//
	// System.out.println("*****************SORTING KUCUK ASC VE SADECE 1-2 NESNE*********************");
	// System.out.println(sortingField);
	// System.out.println("********************************************");
	//
	// criteria.addOrder(Order.asc(sortingField));
	// }
	//
	//
	//
	// }
	//
	// } else {
	//
	// if (isAyrilmisSortingListesiBuyuk) {
	//
	// System.out.println("*****************SORTING BUYUK DESC *********************");
	// System.out.println(aliasList[aliasList.length - 2] + "." +
	// aliasList[aliasList.length - 1]);
	// System.out.println("********************************************");
	//
	// criteria.addOrder(Order.desc(aliasList[aliasList.length - 2] + "." +
	// aliasList[aliasList.length - 1]));
	//
	// } else {
	//
	// if(ayrilmisSortingListesi.length > 2) {
	//
	// System.out.println("*****************SORTING KUCUK DESC*********************");
	// System.out.println(ayrilmisSortingListesi[ayrilmisSortingListesi.length -
	// 2] + "." + ayrilmisSortingListesi[ayrilmisSortingListesi.length - 1]);
	// System.out.println("********************************************");
	//
	// criteria.addOrder(Order.desc(ayrilmisSortingListesi[ayrilmisSortingListesi.length
	// - 2] + "." + ayrilmisSortingListesi[aliasList.length - 1]));
	//
	// } else {
	//
	// System.out.println("*****************SORTING KUCUK DESC VE SADECE 1-2 NESNE*********************");
	// System.out.println(sortingField);
	// System.out.println("********************************************");
	//
	// criteria.addOrder(Order.desc(sortingField));
	// }
	// }
	// }
	//
	// } else {
	//
	// if (ASC) {
	//
	// criteria.addOrder(Order.asc(sortingField));
	// } else {
	//
	// criteria.addOrder(Order.desc(sortingField));
	// }
	// }
	// }
	// }

}