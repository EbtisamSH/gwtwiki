package info.bliki.gae.controller;

import info.bliki.gae.db.PageService;
import info.bliki.gae.model.BlikiUtil;
import info.bliki.gae.model.Page;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PageViewController {
  protected final Log logger = LogFactory.getLog(getClass());
  @Autowired
  private PageService pageService;

  @RequestMapping(value = "/p/{key}", method = RequestMethod.GET)
  public String indexP(@PathVariable String key, Model model) {
    Page page = pageService.findByKey(Long.valueOf(key));
    if (page == null) {
      return "common/404";
    }
    model.addAttribute("page", page);
    return "page/view";
  }

  @RequestMapping(value = "/wiki/{key}", method = RequestMethod.GET)
  public String indexW(@PathVariable String key, Model model) {
    if (StringUtils.isBlank(key)) {
      return "common/404";
    }
    String topicName = BlikiUtil.decodeTitle(key);
    Page page = pageService.findByTitle(topicName);
    if (page == null) {
      model.addAttribute("page", new Page(topicName, ""));
      return PageController.NEW_PAGE_URI;
      // return "/page/new.jsp?title=" + key;
    }
    model.addAttribute("page", page);
    return "page/view";
  }
}
