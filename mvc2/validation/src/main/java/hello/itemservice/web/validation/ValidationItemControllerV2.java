package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v4/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes) {

        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
        }

        if(item.getPrice() == null || item.getPrice() <= 1000 || item.getPrice() >= 1000000){
            bindingResult.addError(new FieldError("item","price","가격은 1,000이상 1,000,000만원 사이의 값으로 필수 입니다."));
        }

        if(item.getQuantity() == null || item.getQuantity() > 10000){
            bindingResult.addError(new FieldError("item","quantity","수량은 9999이하로 필수 입니다."));
        }

        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){
                bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        // 검증 실패시, 다시 입력폼.
//        if(hasError(errors)){
//            model.addAttribute("errors",errors);
//            log.info("errors = {}",errors);
//            return "validation/v4/addForm";
//        }

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v4/addForm";
        }

        // 만약 오류가 없는건 어떻게 처리하지?
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes) {

        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(),false, null, null, "상품 이름은 필수입니다."));
        }

        if(item.getPrice() == null || item.getPrice() <= 1000 || item.getPrice() >= 1000000){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false, null, null,"가격은 1,000이상 1,000,000만원 사이의 값으로 필수 입니다."));
        }

        if(item.getQuantity() == null || item.getQuantity() > 10000){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,null,null,"수량은 9999이하로 필수 입니다."));
        }

        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){
                bindingResult.addError(new ObjectError("item",null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v4/addForm";
        }

        // 만약 오류가 없는건 어떻게 처리하지?
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes) {

        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");

        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(),false, new String[]{"required.item.itemName"}, null, null));
        }

        if(item.getPrice() == null || item.getPrice() <= 1000 || item.getPrice() >= 1000000){
            bindingResult.addError(new FieldError("item","price", item.getPrice(),false, new String[]{"range.item.price"}, new Object[]{1000,1000000},"가격은 1,000이상 1,000,000만원 사이의 값으로 필수 입니다."));
        }

        if(item.getQuantity() == null || item.getQuantity() > 10000){
            bindingResult.addError(new FieldError("item","quantity", item.getQuantity(),false, new String[]{"max.item.quantity"}, new Object[]{9999},"수량은 9999이하로 필수 입니다."));
        }

        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){
                bindingResult.addError(new ObjectError("item",null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v4/addForm";
        }

        // 만약 오류가 없는건 어떻게 처리하지?
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    //    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes) {

        itemValidator.validate(item, bindingResult);

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v4/addForm";
        }

        // 만약 오류가 없는건 어떻게 처리하지?
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    //    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "validation/v4/addForm";
        }

        // 만약 오류가 없는건 어떻게 처리하지?
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    private boolean hasError(Map<String, String> errors) {
        return !errors.isEmpty();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v4/items/{itemId}";
    }

}

