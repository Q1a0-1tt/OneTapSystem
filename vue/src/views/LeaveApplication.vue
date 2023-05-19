  <template>
    <div>
      <div class="container">
        <form @submit.prevent="submitForm" class="form">
          <div class="form-group">
            <label for="name">学生学号：</label>
            <input type="text" id="userid" v-model="leave.userid" required class="form-control">
          </div>
          <div class="form-group">
            <label for="name">学生姓名：</label>
            <input type="text" id="name" v-model="leave.name" required class="form-control">
          </div>
          <div class="form-group">
            <label for="leave-date">请假时间：</label>
            <input type="datetime-local" id="leave-date" v-model="leave.starttime" required class="form-control">
          </div>
          <div class="form-group">
            <label for="return-date">返回时间：</label>
            <input type="datetime-local" id="return-date" v-model="leave.finaltime" required class="form-control">
          </div>
          <div class="form-group">
            <label for="destination">目的地：</label>
            <input type="text" id="destination" v-model="leave.destination" required class="form-control">
          </div>
          <div class="form-group">
            <label for="reason">理由：</label>
            <textarea id="reason" v-model="leave.reason" required class="form-control"></textarea>
          </div>
          <button type="submit" class="btn btn-primary ml-auto">提交</button>
        </form>
      </div>
    </div>


  </template>

  <script>
  export default {
    name: "LeaveApplication",
    data() {
      return {
        leave: {
          userid:'',
          name: '',
          starttime: '',
          finaltime: '',
          destination: '',
          reason: ''
        },
        active:'',
        showNameError: false,
        showStartDateError: false,
        showEndDateError: false,
        showDestinationError: false,
        showReasonError: false,
      }
    },
    methods:{
      submitForm() {
        this.request.post("/leave",this.leave).then(res=>{
          if(res.code=="200"){
            this.$message.success("提交成功")
            this.leave = {
              userid:'', name: '', starttime: '', finaltime: '', destination: '', reason: ''
            };
          }else{
            this.$message.error("提交失败")
          }
        })
      }
    }
  }
  </script>

  <style scoped>
  .container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }

  .form {
    background-color: #ffffff;
    border-radius: 10px;
    padding: 40px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  .form-group {
    margin-bottom: 20px;
  }

  label {
    font-size: 16px;
    font-weight: bold;
    display: block;
    margin-bottom: 10px;
  }

  input[type="text"],
  textarea {
    width: 100%;
    padding: 8px;
    font-size: 16px;
    border: none;
    border-radius: 5px;
    background-color: #fff;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  }

  input[type="datetime-local"] {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border: none;
    border-radius: 5px;
    background-color: #fff;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  }

  textarea {
    height: 150px;
  }

  .btn {
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    font-size: 16px;
  }

  .ml-auto {
    margin-right: auto;
  }

  .error {
    color: red;
  }
  </style>
