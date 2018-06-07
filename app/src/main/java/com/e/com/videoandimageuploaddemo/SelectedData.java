package com.e.com.videoandimageuploaddemo;

import android.os.Parcel;
import android.os.Parcelable;

class SelectedData implements Parcelable
    {
        int id;
        String url;
        String type;
        boolean isSolved;
        boolean isChecked;
        DataType dataType;

        protected SelectedData(Parcel in)
            {
                id = in.readInt();
                url = in.readString();
                type = in.readString();
                isSolved = in.readByte() != 0;
                isChecked = in.readByte() != 0;
            }

        public SelectedData()
            {

            }

        public static final Creator<SelectedData> CREATOR = new Creator<SelectedData>()
            {
                @Override
                public SelectedData createFromParcel(Parcel in)
                    {
                        return new SelectedData(in);
                    }

                @Override
                public SelectedData[] newArray(int size)
                    {
                        return new SelectedData[size];
                    }
            };

        public int getId()
            {
                return id;
            }

        public void setId(int id)
            {
                this.id = id;
            }

        public String getUrl()
            {
                return url;
            }

        public void setUrl(String url)
            {
                this.url = url;
            }

        public boolean isSolved()
            {
                return isSolved;
            }

        public void setSolved(boolean solved)
            {
                isSolved = solved;
            }

        public String getType()
            {
                return type;
            }

        public void setType(String type)
            {
                this.type = type;
            }

        public DataType getDataType()
            {
                return dataType;
            }

        public void setDataType(DataType dataType)
            {
                this.dataType = dataType;
            }

        public boolean isChecked()
            {
                return isChecked;
            }

        public void setChecked(boolean checked)
            {
                isChecked = checked;
            }

        @Override
        public int describeContents()
            {
                return 0;
            }

        @Override
        public void writeToParcel(Parcel dest, int flags)
            {
                dest.writeInt(id);
                dest.writeString(url);
                dest.writeString(type);
                dest.writeByte((byte) (isSolved ? 1 : 0));
                dest.writeByte((byte) (isChecked ? 1 : 0));
            }
    }
