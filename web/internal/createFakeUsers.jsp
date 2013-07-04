<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dao.*"%>
<%@page import="com.util.*"%>

<%
    String cmd = request.getParameter("cmd");
    if("create".equals(cmd)) {
        try {
            String prefix = "guest";
            String suffix = "@yahoo.com";
            String ccn = "4111111111111111";
            String cardType = CreditCardDetail.CreditCardType.VISA.getValue();
            Date expDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/06/2014");
            Date now = new Date();
            String street = "El Camino Real";
            String city = "San Mateo";
            String state = "CA";
            String zip = "94402";
            String country = "United States";
            String phone = "2223334444";
            String company = "Fake Company";

            List<Deal> activeDeals = Deal.getActiveDeals();
            if(activeDeals != null)
                out.println(activeDeals.size() + " active deals found<br/>");
            Random random = new Random();
            int count = 0;
            
            for(int i = 12; i <= 62; i++) {
                String username = prefix + String.valueOf(i);
                String password = username;
                String email = username + suffix;

                User newUser = new User();
                newUser.setType(User.UserType.BUYER.getValue());
                newUser.setRegistrationTime(new Date());
                newUser.setStatus(User.UserStatus.ACTIVE.getValue());
                newUser.setEmailVerified(true);
                newUser.setEmail(email);
                newUser.setUsername(username);
                newUser.setPassword(AuthUtil.encryptWithMD5(password));
                newUser.save();

                CreditCardDetail ccd = new CreditCardDetail();
                ccd.setFirstName(username);
                ccd.setLastName(username);
                ccd.setAddress1(String.valueOf(i) + " " + street);
                ccd.setAddress2("");
                ccd.setCity(city);
                ccd.setState(state);
                ccd.setZipCode(zip);
                ccd.setCountry(country);
                ccd.setCardholderName(username + " " + username);
                ccd.setCardType(cardType);
                ccd.setLastFour(CreditCardUtil.getEncodedLastFourDigits(ccn));
                ccd.setExpiryDate(expDate);
                ccd.setUserId(newUser.getUserId());
                ccd.setDateCreated(now);
                ccd.setDateModified(now);
                ccd.save();

                ShippingAddress shipAddr = new ShippingAddress();
                shipAddr.setCompany(company + " " + String.valueOf(i));
                shipAddr.setFirstName(username);
                shipAddr.setLastName(username);
                shipAddr.setAddress1(String.valueOf(i) + " " + street);
                shipAddr.setAddress2("");
                shipAddr.setCity(city);
                shipAddr.setState(state);
                shipAddr.setZipCode(zip);
                shipAddr.setCountry(country);
                shipAddr.setPhoneNumber(phone);
                shipAddr.setUserId(newUser.getUserId());
                shipAddr.save();

                int randomNum = random.nextInt(10);
                int dealNum = 1;
                int joinCount = 0;
                for(Deal deal : activeDeals) {
                    List<DealParticipant> participants = DealParticipant.getByDealId(deal.getDealId());
                    if(participants == null) {
                        dealNum++;
                        continue;
                    }

                    if(participants.size() > Deal.getMaxAllowedParticipants(deal.getDealId())-2) {
                        dealNum++;
                        continue;
                    }

                    if(dealNum < randomNum) {
                        dealNum++;
                        continue;
                    }

                    DealPricingOption dpo = DealPricingOption.getLowestPriceOption(deal.getDealId());

                    OrderSummary os = new OrderSummary();
                    os.setQuantity(1);
                    os.setUnitPrice(dpo.getPrice());
                    os.setShippingMethod("ground");
                    os.setTax(os.getQuantity() * os.getUnitPrice() * 0.0925);
                    os.setShippingCost(6.99);
                    os.save();
                    
                    DealParticipant dp = new DealParticipant();
                    dp.setBuyerId(newUser.getUserId());
                    dp.setDealId(deal.getDealId());
                    dp.setPricingOptionId(dpo.getOptionId());
                    dp.setCreditCardId(ccd.getCreditCardDetailsId());
                    dp.setShippingAddressId(shipAddr.getShippingAddressId());
                    dp.setJoinDate(now);
                    dp.setOrderSummaryId(os.getOrderSummaryId());
                    dp.save();
                    joinCount++;
                }
                out.println("User " + newUser.getUserId() + " has joined " + joinCount + " deals<br/>");
                count++;
            }
            out.println(count + " users have been created");
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }
%>
