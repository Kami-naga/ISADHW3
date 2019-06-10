export default{
  port:"http://localhost:8080",
  user:{
    id:"",
    name:"未登录",
    avatar:"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1560095880&di=6c3c14f3ec86fa255b75e6ee0546e7f3&src=http://hbimg.b0.upaiyun.com/7842c3e9b5c38401e94851097c0e29f0b48c5f884d66-x9BbFI_fw658",
    role:"trader"
  },
  book:{},
  broker:{},
  product:{},
  instruments:{},
  products:[
    {
      id:0,
      name:"铜",
      ename:"Cu"
    },
    {
      id:1,
      name:"铝",
      ename:"Al"
    },
    {
      id:2,
      name:"锌",
      ename:"Zn"
    },
    {
      id:3,
      name:"铅",
      ename:"Pb"
    },
    {
      id:4,
      name:"镍",
      ename:"Ne"
    },
    {
      id:5,
      name:"铜",
      ename:"togo1"
    },
    {
      id:6,
      name:"铝",
      ename:"togo2"
    },
    {
      id:7,
      name:"锌",
      ename:"togo3"
    },
    {
      id:8,
      name:"铅",
      ename:"togo4"
    },
    {
      id:9,
      name:"镍",
      ename:"togo5"
    },
    {
      id:10,
      name:"铜",
      ename:"togo6"
    },
    {
      id:11,
      name:"铝",
      ename:"togo7"
    },
    {
      id:12,
      name:"锌",
      ename:"togo8"
    },
    {
      id:13,
      name:"铅",
      ename:"togo9"
    },
    {
      id:14,
      name:"镍",
      ename:"togo10"
    }
  ],
  brokers:[
    {
      id:0,
      name:"Broker M"
    },
    {
      id:1,
      name:"Broker W"
    },
    {
      id:2,
      name:"Broker S"
    },
    {
      id:3,
      name:"Broker G"
    },
  ],
  booksData:[
    {
      id:0,
      bookName:"Cu1109"
    },
    {
      id:1,
      bookName:"Cu1110"
    },
  ],
  trades:[
    {
      trade_id:312393,
      broker:"M",
      product:"Gold Swaps",
      period:"SEP16",
      price:1245,
      qty:50,
      initiator:{
        trader:'Sam Wang',
        company:"ABC Corp",
        side:"sell"
      },
      completion:{
        trader:"Sixian Liu",
        company:"MS",
        side:"buy"
      }
    }
  ],
  sells:[
    {
      price:1250,
      vol:50
    },
    {
      price:1260,
      vol:60
    },
    {
      price:1270,
      vol:70
    },
    {
      price:1280,
      vol:80
    },
    {
      price:1290,
      vol:90
    },
    {
      price:1300,
      vol:100
    }
  ],
  buys:[
    {
      price:1240,
      vol:140
    },
    {
      price:1230,
      vol:130
    },
    {
      price:1220,
      vol:120
    },
    {
      price:1210,
      vol:110
    }
  ]
}
