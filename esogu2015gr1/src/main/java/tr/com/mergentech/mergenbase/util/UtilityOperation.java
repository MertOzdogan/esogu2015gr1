package tr.com.mergentech.mergenbase.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Version;

public class UtilityOperation {

	/**
	 * 
	 * @param dbEntity
	 * @param detachedDonem
	 * 
	 * @author Yusuf KARTAL
	 * @param <T>
	 */
	public static <T> void copyValues(T dbEntity, T detachedEntity) {

		try {
			// İşlem yapılan sınıf metotlarına ihtiyaç duyulacak
			Class c = dbEntity.getClass();

			// ilgili sınıfın methodları bir diziye alınıyor.
			Method[] methods = c.getDeclaredMethods();

			// Her metot için gerekli eşitlemenin yapılması gerekiyor.
			for (Method method : methods) {

				// set metodları bez alınıyor set metodu yapıldıktan sonra bir
				// get metodları için aynı işlemleri yapmaya gerek yok.
				if (method.getName().startsWith("set")) {

					// ele alanın set metoduna karşılık get metodu da
					// kullanılacak
					Method getMethod = c.getDeclaredMethod(method.getName().replace("set", "get"), null);

					// eğer veritabanında bir sutuna karşılık gelen bir metodsa
					// annotation'ı alınır.
					Column annotation = getMethod.getAnnotation(Column.class);

					// Eğer veritabanında bir foreign keyse
					JoinColumn joinAnnotation = getMethod.getAnnotation(JoinColumn.class);

					if ((getMethod.invoke(detachedEntity, null) == null // guncelleme için gelen nesnede veri yoksa
							&& (annotation != null && annotation.nullable() == false)) // ve bu alan null olamaz deniyorsa eski veri kalmalı
							|| (getMethod.invoke(detachedEntity, null) == null // veya yine guncelleme için gelen nesnede veri yoksa
							&& (joinAnnotation != null && joinAnnotation.nullable() == false)) // ve annotation değilde joinannaotion kullanıldıysa ve bu alan null olamaz deniyorsa eski veri kalmalı
					){
						method.invoke(detachedEntity, getMethod.invoke(dbEntity, null));
					}

				}
			}

			c = dbEntity.getClass().getSuperclass();
			methods = c.getDeclaredMethods();

			for (Method method : methods) {

				if (method.getName().startsWith("set") && c.getDeclaredMethod(method.getName().replace("set", "get"), null).getAnnotation(Version.class) == null) {

					Method getMethod = c.getDeclaredMethod(method.getName().replace("set", "get"), null);
					Column annotation = getMethod.getAnnotation(Column.class);
					JoinColumn joinAnnotation = getMethod.getAnnotation(JoinColumn.class);

					if ((getMethod.invoke(detachedEntity, null) == null && (annotation != null && annotation.nullable() == false))
							|| (getMethod.invoke(detachedEntity, null) == null && (joinAnnotation != null && joinAnnotation.nullable() == false))) {

						// TODO sysout satıları silinecek.
						//System.out.println();
						//System.out.println(getMethod.getName() + "-" + getMethod.invoke(detachedEntity, null));

						method.invoke(detachedEntity, getMethod.invoke(dbEntity, null));
					}
				}
			}

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}