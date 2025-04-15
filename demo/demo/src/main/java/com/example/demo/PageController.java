@Controller 
  public class PageController {
    @GetMapping("/index")
    public String indexPage(){
      return "index";
    }
  }
