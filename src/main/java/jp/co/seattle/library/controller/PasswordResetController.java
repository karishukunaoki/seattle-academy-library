package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.UsersService;

@Controller
public class PasswordResetController {
	final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/passwordReset", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	public String createAccount(Model model) {
		return "passwordReset";
		//上記で文字列だけを返すと文字列の後に.jsbをつけて返す事ができる。

	}

	/**
	 * 新規アカウント作成
	 *
	 * @param email            メールアドレス
	 * @param password         パスワード
	 * @param passwordForCheck 確認用パスワード
	 * @param model
	 * @return ホーム画面に遷移
	 */
	@Transactional
	@RequestMapping(value = "/passwordReset", method = RequestMethod.POST)
	public String passwordReset(Locale locale, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
			Model model) {
		//.jspのformで囲まれた情報が上記の処理に来る
		// デバッグ用ログ
		logger.info("Welcome passwprdReset! The client locale is {}.", locale);
		//左のコンソールに表示されるログを作っている
		// バリデーションチェック、パスワード一致チェック（タスク１）

		if (password.length() >= 8 && password.matches("^[a-zA-Z0-9]+$")) {
			if (password.equals(passwordForCheck)) {
				// パラメータで受け取ったアカウント情報をDtoに格納する。
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(email);
				//userInfoにsetで情報を入れる（set）する。それを58行目のUserInfoに移動させて保存する

				userInfo.setPassword(password);
				usersService.registUser(userInfo);
				//26行目で定義する。
				//userService.javaの中のregistIserにuserInfoの情報を入れる。
				return "redirect:/";
			} else {
				model.addAttribute("errorMessage", "パスワードが一致しません。");
				//上記とmodelが一致していたら、下の処理を経てエラー分を表示する。
				//modelとはjsbからもjavaからもアクセスできる。
				return "passwprdReset";
				//上記のif文が間違っていた場合また.jspから処理を始める
			}
		} else {
			model.addAttribute("errorMessage", "パスワードは8文字以上かつ半角英数字に設定してください。");
			return "passwordReset";
		}
	}

	// パラメータで受け取ったアカウント情報をDtoに格納する。

}