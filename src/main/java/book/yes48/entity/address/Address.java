package book.yes48.entity.address;

import book.yes48.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@RequiredArgsConstructor
@Getter @Setter
public class Address {

    private String postcode;
    private String basicAddress;
    private String detailsAddress;
    private String extraAddress;
}
