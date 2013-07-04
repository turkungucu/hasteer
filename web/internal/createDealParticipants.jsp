<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dao.*"%>
<%@page import="com.util.*"%>

<%
    String cmd = request.getParameter("cmd");
    if("create".equals(cmd)) {
        try {
            long startUserId = Long.parseLong(request.getParameter("suid"));
            long endUserId = Long.parseLong(request.getParameter("euid"));
            List<Deal> activeDeals = Deal.getActiveDeals();
            Date now = new Date();
            if(activeDeals != null)
                out.println(activeDeals.size() + " active deals found<br/>");

            Random random = new Random();
            int count = 0;

            for(long i = startUserId; i <= endUserId; i++) {
                User user = User.getUserById(i);
                if(user == null)
                    continue;

                List<CreditCardDetail> ccds = CreditCardDetail.getByUserId(i);
                if(ccds == null || ccds.size() == 0)
                    continue;

                CreditCardDetail ccd = ccds.get(0);

                List<ShippingAddress> addrs = ShippingAddress.getByUserId(i);
                if(addrs == null || addrs.size() == 0)
                    continue;

                ShippingAddress addr = addrs.get(0);
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
                    os.setShippingCost(6.99);
                    os.setTax(os.getQuantity() * os.getUnitPrice() * 0.0925);
                    os.save();

                    DealParticipant dp = new DealParticipant();
                    dp.setBuyerId(i);
                    dp.setDealId(deal.getDealId());
                    dp.setPricingOptionId(dpo.getOptionId());
                    dp.setCreditCardId(ccd.getCreditCardDetailsId());
                    dp.setShippingAddressId(addr.getShippingAddressId());
                    dp.setJoinDate(now);
                    dp.setOrderSummaryId(os.getOrderSummaryId());
                    dp.save();
                    joinCount++;
                }
                out.println("User " + i + " has joined " + joinCount + " deals<br/>");
                count++;
            }
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }
%>