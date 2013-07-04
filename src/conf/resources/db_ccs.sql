-- cc #1: emre
alter table deal_participants add redeemed_points int(10);
alter table deal_participants add referrer_id bigint(10);
alter table deal_participants add referral_sources varchar(45);
alter table deal_participants add ip varchar(16);

alter table deal_participants_log add ip varchar(16);

-- cc #2: emre - 11/5/2010
alter table deal_participants add transaction_id varchar(45);
alter table credit_card_details add vault_token varchar(45);

-- cc #3: alinur - 11/22/2010
ALTER TABLE order_summary ADD COLUMN courier VARCHAR(45) NOT NULL, ADD COLUMN shipped_date DATETIME NULL, ADD COLUMN tracking_id VARCHAR(45) NULL;

-- cc #4: alinur - 11/24/2010
CREATE  TABLE IF NOT EXISTS `hasteer`.`contract_acceptance_log` (
  `acceptance_id` BIGINT(20) NOT NULL ,
  `user_id` BIGINT(20) NOT NULL ,
  `contract_id` BIGINT(20) NOT NULL ,
  `agreed_date` DATETIME NOT NULL ,
  `ip_address` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`acceptance_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `hasteer`.`service_contracts` (
  `contract_id` BIGINT(20) NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  `contents` BLOB NOT NULL ,
  `revision_date` DATETIME NOT NULL ,
  PRIMARY KEY (`contract_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

-- cc #5: alinur - 11/24/2010

insert into service_contracts (contract_id, type, contents, revision_date) values (1, 'rewards-terms-of-service','<h2>Referral Rewards Terms of Service</h2>
        <p>
            Referral points is our way of thanking you for bringing people into
            a deal and help achieve the best price. Each product page will clearly
            display how many points you can earn per qualified referral.
        </p>
        <h3>Details:</h3>
        <ul>
            <li>
                You must be logged into your account for proper tracking of your
                referrals.
            </li>
            <li style="margin-top: 5px;">
                Simply use Facebook Like button or the share button to share a
                deal with friends and family.
            </li>
            <li style="margin-top: 5px;">
                Points are added to your account when referred accounts purchase
                the product. You do not have to join the deal that you are
                referring to others.
            </li>
            <li style="margin-top: 5px;">
                Referral Reward Points can only be redeemed when you join a
                deal.
            </li>
            <li style="margin-top: 5px;">
                Exchange rate is <%=exchangeRate%> points = $1 and points can
                only be redeemed at <%=exchangeRate%> point increments.
            </li>
        </ul>',curdate());

-- cc #6: emre - 11/18/2010
CREATE TABLE tasks (
  task_id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  task_name varchar(45) NOT NULL,
  class_name varchar(200) NOT NULL,
  period int(10) unsigned DEFAULT '0',
  delay int(10) unsigned DEFAULT '0',
  enabled tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (task_id),
  UNIQUE KEY task_name_idx (task_name)
) ENGINE=InnoDB;

-- cc #7: emre
CREATE TABLE `braintree_errors` (
  `error_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `error_code` varchar(10) DEFAULT NULL,
  `error_text` text,
  `transaction_status` int(10) unsigned DEFAULT NULL,
  `error_time` datetime NOT NULL,
  PRIMARY KEY (`error_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- cc #8: emre
CREATE TABLE `admin_string` (
  `name` varchar(200) NOT NULL,
  `value` varchar(200) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- cc #9:
alter table credit_card_details add is_deleted tinyint(1) not null;
alter table shipping_addresses add is_deleted tinyint(1) not null;

-- cc #10:

CREATE TABLE `merchant_policies` (
  `merchant_policy_id` bigint(20) NOT NULL,
  `seller_id` bigint(20) NOT NULL,
  `policy_type` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  `content` blob NOT NULL,
  `date_added` datetime NOT NULL,
  `date_modified` datetime NOT NULL,
  PRIMARY KEY (`merchant_policy_id`),
  UNIQUE KEY `merchant_policy_id_UNIQUE` (`merchant_policy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

INSERT INTO `merchant_policies` (`merchant_policy_id`, `seller_id`, `policy_type`, `description`, `content`, `date_added`, `date_modified`)
VALUES (1,77,'shipping-policy','Shipping Policy','Product ships in 2-3 days.',curdate(),curdate());

INSERT INTO `merchant_policies` (`merchant_policy_id`, `seller_id`, `policy_type`, `description`, `content`, `date_added`, `date_modified`)
VALUES (2,77,'return-policy','Return Policy - 30 Day','We will give you a full refund on new and unopened items that you purchase, if you return them within 30 days of delivery.',curdate(),curdate());

ALTER TABLE `deals` ADD COLUMN `return_policy_id` BIGINT NOT NULL AFTER `retail_price` ,
ADD COLUMN `shipping_policy_id` BIGINT NOT NULL  AFTER `retail_price` ,
CHANGE COLUMN `modified_by` `modified_by` VARCHAR(255) NULL DEFAULT NULL,
ADD UNIQUE INDEX `deal_id_UNIQUE` (`deal_id` ASC) ;

update deals set return_policy_id = 2, shipping_policy_id = 1 where deal_id > 1;

-- cc #11 - Emre
ALTER TABLE braintree_errors MODIFY COLUMN transaction_status INT(10) DEFAULT NULL;

-- cc #12 - Emre
alter table deals add processed_date timestamp null;

-- cc #13 Emre
alter table credit_card_details add is_most_recent tinyint(1);
alter table shipping_addresses add is_most_recent tinyint(1);

-- cc #14 Emre
alter table credit_card_details modify is_deleted tinyint(1) default null;
alter table shipping_addresses modify is_deleted tinyint(1) default null;
alter table shipping_addresses modify phone_number varchar(20) default null;

-- cc #15 Emre
update credit_card_details set is_deleted = 0 where is_deleted is null;
update credit_card_details set is_most_recent = 0 where is_most_recent is null;
update shipping_addresses set is_deleted = 0 where is_deleted is null;
update shipping_addresses set is_most_recent = 0 where is_most_recent is null;

alter table credit_card_details modify column is_deleted tinyint(1) not null default 0;
alter table credit_card_details modify column is_most_recent tinyint(1) not null default 0;
alter table shipping_addresses modify column is_deleted tinyint(1) not null default 0;
alter table shipping_addresses modify column is_most_recent tinyint(1) not null default 0;
