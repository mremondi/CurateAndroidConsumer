package curatetechnologies.com.curate_consumer.network.Builders;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.domain.model.MenuSectionModel;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.graphql.api.GetItemByIDQuery;
import curatetechnologies.com.curate_consumer.graphql.api.fragment.MenuInfo;

public class GetItemByIdBuilder {

    public static ItemModel buildItem(GetItemByIDQuery.Data data){
        ItemModel itemModel;
        List<PostModel> posts = new ArrayList<>();
        MenuModel menuModel;
        MenuSectionModel menuSectionModel;
        RestaurantModel restaurantModel;

        if (data.items().size() < 1) {
            return null;
        }
        GetItemByIDQuery.Item item = data.items().get(0);


        menuModel = buildMenu(item.fragments().menuInfo().menu());
        restaurantModel = buildRestaurant(item.fragments().menuInfo().menu().restaurant());
        menuSectionModel = buildMenuSection(item.fragments().menuInfo().menuSection());

        posts = buildPosts(item.posts());

        String imageURL;

        //Have to check if the posts array actually contain a post to grab an image from.
        if (item.posts().size() > 0) {
            imageURL = item.posts().get(0).imageUrl();
        } else {
            imageURL = null;
        }


        itemModel = new ItemModel(item.id(), item.name(), item.description(), imageURL,
                String.format("%.2f", item.distance())+ "mi", "$" + String.format("%.2f", item.price()), item.price(), item.rating(),
                restaurantModel.getName(), menuModel.getName(), menuSectionModel.getName(), restaurantModel.getId(),
                menuModel.getId(), restaurantModel.getStripeID(), item.available(), posts);

        return itemModel;

    }

    private static MenuModel buildMenu(MenuInfo.Menu menu){
        return new MenuModel(menu.id(), menu.name(), 0, null);
    }

    private static MenuSectionModel buildMenuSection(MenuInfo.MenuSection menuSection){
        return new MenuSectionModel(menuSection.id(), menuSection.section(), null);
    }

    private static RestaurantModel buildRestaurant(MenuInfo.Restaurant restaurant){
        return new RestaurantModel(restaurant.id(), restaurant.name(), "", "",
                null, 0.0, "", "", null,
                "", restaurant.stripeId(), 0.0);
    }

    private static List<PostModel> buildPosts(List<GetItemByIDQuery.Post> posts){
        List<PostModel> postModels = new ArrayList<>();
        for (GetItemByIDQuery.Post post: posts){
            String postType;
            if (post.imageUrl() != null){
                if (!post.imageUrl().equals("")) {
                    postType = PostModel.IMAGE_POST;
                } else {
                    postType = PostModel.RATING_POST;
                }
            } else {
                postType = PostModel.RATING_POST;
            }
            postModels.add(new PostModel(post.id(), postType, post.restaurant().id(), 0,
                    "", post.rating(), 0, 0, post.imageUrl(),
                    post.time(), 0, "", "", "",
                    "", 0.0, ""));
        }
        return postModels;
    }
}
