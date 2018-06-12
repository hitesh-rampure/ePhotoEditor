package com.e.com.videoandimageuploaddemo;


public enum DataType
    {

        ITEM_TYPE_PICTURES(0), ITEM_TYPE_VIDEOS(1), ITEM_TYPE_DOCUMENTS(2);

        private int _type;

        DataType(int type)
            {
                _type = type;
            }

        public DataType getItemType(String type)
            {
                switch (type.toLowerCase())
                    {
                        case "pictures":
                            return ITEM_TYPE_PICTURES;
                        case "videos":
                            return ITEM_TYPE_VIDEOS;
                        case "documents":
                            return ITEM_TYPE_DOCUMENTS;
                    }
                return ITEM_TYPE_PICTURES;
            }

        public int getType()
            {
                return _type;
            }

    }
