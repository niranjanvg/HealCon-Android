package util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageDecoder {

	// decode image from byte array
	public static Drawable makeDrawable(Resources resources, byte[] imgBytes) {
		Bitmap bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
		Drawable drawable =new BitmapDrawable(resources, bmp);
		return drawable;
	}
}
