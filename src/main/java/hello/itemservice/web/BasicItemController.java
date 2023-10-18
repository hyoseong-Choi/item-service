package hello.itemservice.web;

import hello.itemservice.domain.ItemRepository;
import hello.itemservice.domain.item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;
    
    @GetMapping
    public String items(Model model) {
        List<item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
    
    @PostConstruct
    public void init() {
        itemRepository.save(new item("A", 10000, 10));
        itemRepository.save(new item("B", 20000, 20));
    }
    
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }
    
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }
    
    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     */
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }
    
    @PostMapping("/add")
    public String addItemV6(@ModelAttribute item item, RedirectAttributes redirectAttributes) {
        item save = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", save.getId());
        redirectAttributes.addAttribute("status", true);
        
        return "redirect:/basic/items/{itemId}";
    }
    
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute item item) {
        itemRepository.update(itemId, item);
        return"redirect:/basic/items/{itemId}";
    }
}
