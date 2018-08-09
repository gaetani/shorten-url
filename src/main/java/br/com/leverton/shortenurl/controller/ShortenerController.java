package br.com.leverton.shortenurl.controller;


import br.com.leverton.shortenurl.domain.Shortener;
import br.com.leverton.shortenurl.service.IShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class ShortenerController {

    @Autowired
    private IShortenerService shortenerService;

    @PutMapping
    public Shortener shortIt( String url, String alias) throws Exception {
        return shortenerService.shortIt(url, alias);
    }


    @GetMapping("/u")
    public String getAlias(@PathVariable("id") String alias, HttpServletResponse httpServletResponse) throws Exception {
        String url = shortenerService.getUrl(alias).getUrl();
        httpServletResponse.sendRedirect(url);
        return url;
    }


}
