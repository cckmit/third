package pojo;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "sxlx",
        "sjcs"
})
@XmlRootElement(name = "Request")
public class FetchBizInfoRequest {
    @XmlElement(required = true)
    @XmlSchemaType(name = "NCName")
    protected String sxlx;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String sjcs;

    public String getSxlx() {

        return sxlx;
    }

    public void setSxlx(String sxlx) {
        this.sxlx = sxlx;
    }

    public String getSjcs() {
        return sjcs;
    }

    public void setSjcs(String sjcs) {
        this.sjcs = sjcs;
    }

    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        String str =new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
        System.out.println(str);
    }
}
