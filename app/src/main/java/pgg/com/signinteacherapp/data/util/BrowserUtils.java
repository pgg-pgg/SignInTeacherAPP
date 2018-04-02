package pgg.com.signinteacherapp.data.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by PDD on 2018/3/20.
 */

public class BrowserUtils {

    public static void copyToClipBoard(Context context,String text){
        ClipboardManager cm=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("coderfun_copy",text));
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }

    public static void openInBrowser(Context context,String url){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri parse = Uri.parse(url);
        intent.setData(parse);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "打开失败了喔，没有可打开的应用", Toast.LENGTH_SHORT).show();
        }
    }
}
