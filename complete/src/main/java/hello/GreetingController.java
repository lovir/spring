package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
	
	@RequestMapping("/AutocatRequest")
	public AutocatRespose autocat(@RequestParam(value="vocNo", defaultValue="") String vocNo, @RequestParam(value="content", defaultValue="") String content) {
		Header header = new Header();
		CateList cateList = new CateList(); 
		AutocatList al = new AutocatList(cateList.autoCatResult(vocNo, content));		
		
		return new AutocatRespose(header, al);
    }	
}
