package de.geeksfactory.opacclient.utils;

import android.content.Context;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import de.geeksfactory.opacclient.R;
import de.geeksfactory.opacclient.objects.DetailledItem;

public class PrintUtils {
    public static String printDetails(DetailledItem detailledItem, Context context) {

        InputStream is = context.getResources().openRawResource(R.raw.print_template);
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(r, "print_template.mustache");
        StringWriter sw = new StringWriter();
        try {
            mustache.execute(sw, new PrintData(detailledItem, context)).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    private static class PrintData {
        public DetailledItem item;
        public Strings strings;

        public PrintData(DetailledItem item, Context context) {
            this.item = item;
            this.strings = new Strings(context);
        }

        private static class Strings {
            public String details_head;
            public String copies_head;
            public String volumes;
            public String barcode;
            public String department;
            public String branch;
            public String status;
            public String location;
            public String return_date;
            public String url;

            public Strings(Context context) {
                this.details_head = context.getString(R.string.details_head);
                this.copies_head = context.getString(R.string.copies_head);
                this.volumes = context.getString(R.string.volumes);
                this.barcode = context.getString(R.string.barcode);
                this.department = context.getString(R.string.department);
                this.branch = context.getString(R.string.branch);
                this.status = context.getString(R.string.status);
                this.location = context.getString(R.string.location);
                this.return_date = context.getString(R.string.return_date);
                this.url = context.getString(R.string.url);
            }
        }
    }
}