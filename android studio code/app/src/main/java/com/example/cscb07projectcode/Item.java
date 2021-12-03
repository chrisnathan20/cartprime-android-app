package com.example.cscb07projectcode;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Item implements Parcelable {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String unit;
  //  private boolean available;

    public Item() {
    }

    public Item(String name, String description, double price, int quantity, String unit) {
        setName(name);
        setDescription(description);
        setQuantity(quantity);
        setUnit(unit);
        setPrice(price);

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription(){ return this.description;}

    public double getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getUnit() {
        return this.unit;
    }



    @Override
    public String toString() {
        String x = this.name + " " + this.description + " " + this.price + " " + this.quantity + " " + this.unit;
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return false;
        Item obj = (Item) o;
        if (obj.getName() != this.name &&
                obj.getDescription() != this.description &&
                obj.getPrice() != this.price &&
                obj.getQuantity() != this.quantity &&
                this.unit != obj.getUnit())
            return false;
        return true;
    }

    public boolean getAvailable()
    {
        return this.quantity >0;
    }

    public int hashCode() {
        int x = 0;
        int p = (int) this.price;
        x = this.quantity + p ;
        return x;
    }

    public Item(Parcel in)
    {
        name =in.readString();
        description = in.readString();
        quantity = in.readInt();
        price = in.readDouble();
        unit = in.readString();
       // available = in.readBoolean();

    }

    public static DiffUtil.ItemCallback<Item> itemItemCallback = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getDescription().equals(newItem.getDescription());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeString(unit);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];

        }
    };
}
