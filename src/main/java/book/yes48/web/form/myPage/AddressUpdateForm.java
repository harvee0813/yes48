package book.yes48.web.form.myPage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressUpdateForm {

    String postcode;
    String address;
    String detailsAddress;
    String extraAddress;


    @Builder
    public AddressUpdateForm(String postcode, String address, String detailsAddress, String extraAddress) {
        this.postcode = postcode;
        this.address = address;
        this.detailsAddress = detailsAddress;
        this.extraAddress = extraAddress;
    }
}
