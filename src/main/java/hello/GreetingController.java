package hello;

import java.util.concurrent.atomic.AtomicLong;

import jssc.SerialPortException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private ViscaCtrl viscaCtrl;
    private String psge = "<a href=\"left\"> left </a>\n" +
            "<br>\n" +
            "<a href=\"right\"> right </a>\n" +
            "<br>\n" +
            "<a href=\"up\"> up </a>\n" +
            "<br>\n" +
            "<a href=\"down\"> down </a>\n" +
            "\n" +
            "<br><br>\n" +
            "\n" +
            "<form action=\"run\" method=\"post\">\n" +
            "\n" +
            "<input type=\"text\" name=\"arg\" />\n" +
            "<input type=\"submit\" value=\"Wyślij\" />\n" +
            "\n" +
            "</form>";

    @PostConstruct
    public void init(){
        viscaCtrl = new ViscaCtrl();
        try {
            viscaCtrl.init();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/run", method = RequestMethod.POST)
    public String run(@RequestParam(value="arg", required=false) String arg) throws SerialPortException, InterruptedException {

        System.out.println(arg);
        return viscaCtrl.executeCommand(arg) + "</br></br>" + psge;
    }
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return psge;
    }


    @RequestMapping("/left")
    public String left(@RequestParam(value="name", defaultValue="World") String name) {
        try {
            viscaCtrl.executeCommand("left");
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return psge;
    }
    @RequestMapping("/right")
    public String right(@RequestParam(value="name", defaultValue="World") String name) {
        try {
            viscaCtrl.executeCommand("right");
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

        return psge;
    }
    @RequestMapping("/up")
    public String up(@RequestParam(value="name", defaultValue="World") String name) {

        try {
            viscaCtrl.executeCommand("up");
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return psge;
    }
    @RequestMapping("/down")
    public String down(@RequestParam(value="name", defaultValue="World") String name) {
        try {
            viscaCtrl.executeCommand("down");
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

        return psge;
    }

}
